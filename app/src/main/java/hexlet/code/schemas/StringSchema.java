package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {
    @Override
    public StringSchema required() {
        super.required();
        addCheck("required", value -> !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        addCheck("minLength", value -> value.length() >= length);
        return this;
    }

    public StringSchema contains(String substr) {
        addCheck("contains", value -> value.contains(substr));
        return this;
    }
}
