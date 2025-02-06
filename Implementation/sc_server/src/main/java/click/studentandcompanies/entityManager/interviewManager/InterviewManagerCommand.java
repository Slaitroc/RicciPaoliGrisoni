package click.studentandcompanies.entityManager.interviewManager;

public interface InterviewManagerCommand<T> {
    T execute();
}
