package click.studentandcompanies.entityManager.recommendationProcess;

public interface RecommendationProcessCommand<T> {
    T execute();
}
