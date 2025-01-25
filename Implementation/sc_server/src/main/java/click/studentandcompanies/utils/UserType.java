package click.studentandcompanies.utils;

public enum UserType {
    STUDENT,
    COMPANY,
    UNIVERSITY,
    UNKNOWN;

    public static UserType fromString(String userType) {
        try {
            return UserType.valueOf(userType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
