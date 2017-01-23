package co.samsao.reporter.main;

/**
 * TODO Javadoc
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
}
