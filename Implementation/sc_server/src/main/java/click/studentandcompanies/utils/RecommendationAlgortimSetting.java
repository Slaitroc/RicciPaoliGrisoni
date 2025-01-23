package click.studentandcompanies.utils;

public enum RecommendationAlgortimSetting {
    ALPHA(0.5),
    DAYS_BEFORE_EXPIRATION(3),
    DEFAULT_THRESHOLD(0.2);

    private final double value;

    RecommendationAlgortimSetting(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}