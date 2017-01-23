package co.samsao.reporter.main;

import co.samsao.reporter.IPresenter;

/**
 * Regroups all the methods for the main presenter.
 */
interface IMainPresenter extends IPresenter<IMainView> {

    /**
     * Loads the repositories from the backend.
     */
    void load();
}
