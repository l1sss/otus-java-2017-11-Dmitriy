package ru.otus.testrunner;

import java.util.Objects;

public class Assert {
    public static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            StringBuilder sb = new StringBuilder()
                    .append("expected : ")
                    .append(expected)
                    .append(" (")
                    .append(expected.getClass().getSimpleName())
                    .append("), actual : ")
                    .append(actual)
                    .append(" (")
                    .append(actual.getClass().getSimpleName())
                    .append(")");

            throw new TestError(sb.toString());
        }
    }
}
