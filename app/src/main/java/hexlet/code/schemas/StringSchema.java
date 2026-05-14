package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {
    private int minLength;
    private String substring;

    public StringSchema minLength(int length) {
        minLength = length;
        return this;
    }

    public StringSchema contains(String substr) {
        substring = substr;
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return !required;
        }

        String str = (String) value;

        if (required && str.isEmpty()) {
            return false;
        }

        if (minLength > 0 && str.length() < minLength) {
            return false;
        }

        if (substring != null && !str.contains(substring)) {
            return false;
        }

        return true;
    }
}
