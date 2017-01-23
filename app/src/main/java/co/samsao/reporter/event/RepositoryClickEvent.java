package co.samsao.reporter.event;

import co.samsao.reporter.model.Repository;

/**
 * Custom event triggered when clicking on a repository.
 */
public class RepositoryClickEvent {

    private Repository repository;

    public RepositoryClickEvent(Repository repository_p) {
        repository = repository_p;
    }

    public Repository getRepository() {
        return repository;
    }
}
