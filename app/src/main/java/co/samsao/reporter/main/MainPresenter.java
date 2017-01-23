package co.samsao.reporter.main;

/**
 * Presenter for the list of repositories.
 */
class MainPresenter implements IMainPresenter {

    private IMainView mView;

    @Override
    public void attachView(IMainView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void load() {
        // TODO load from backend
    }
}
