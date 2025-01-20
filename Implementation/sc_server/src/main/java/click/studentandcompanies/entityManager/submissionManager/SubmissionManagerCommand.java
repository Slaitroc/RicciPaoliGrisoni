package click.studentandcompanies.entityManager.submissionManager;

public interface SubmissionManagerCommand<T> {
    T execute();
}
