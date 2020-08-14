package comp3350.group6.homiez.application;

public class Shared {

    public enum QueryResult {
        SUCCESS,
        FAILURE,
        WARNING
    }

    public static boolean isNotNullOrBlank(String param) {
        return param != null && param.trim().length() != 0;
    }

    public static boolean isNotNull (Object o) {
        return o != null;
    }
}
