package co.samsao.reporter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Singleton containing all the repositories.
 * Is used as cache and is supposed to be updated by the backend.
 */
public class RepositoryModel extends Observable {

    private static RepositoryModel instance;

    private List<Repository> repositories;

    private RepositoryModel() {
        repositories = new ArrayList<>();
    }

    public static RepositoryModel getInstance() {
        if (instance == null) {
            instance = new RepositoryModel();
        }
        return instance;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories_p) {
        repositories = repositories_p;
        setChanged();
        notifyObservers();
    }
}
