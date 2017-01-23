package co.samsao.reporter.main;

import java.util.List;

import co.samsao.reporter.MyApplication;
import co.samsao.reporter.model.Repository;
import co.samsao.reporter.model.RepositoryModel;
import co.samsao.reporter.network.NetworkService;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter for the list of repositories.
 */
class MainPresenter implements IMainPresenter {

    private IMainView mView;

    private Subscription mSubscription;

    private List<Repository> mRepositories;

    @Override
    public void attachView(IMainView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

    @Override
    public void load() {
        // showing progress to the user
        mView.showProgressBar();

        // cleaning out the subscription in case of previous call
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        // getting the backend service in the application
        MyApplication application = MyApplication.get(mView.getContext());
        NetworkService service = application.getNetworkService();

        // getting the repositories from the backend
        mSubscription = service.getPublicRepositories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        // updating the model with the new result
                        RepositoryModel.getInstance().setRepositories(mRepositories);
                        // removing the progress bar so the user can see the updated result
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // showing an error message and removing progress bar
                        mView.showMessage(e.getMessage());
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        mRepositories = repositories;
                    }
                });
    }
}
