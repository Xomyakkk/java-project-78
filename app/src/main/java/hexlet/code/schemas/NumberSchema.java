package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Number> {
    @Override
    public NumberSchema required() {
        super.required();
        addCheck("required", value -> value != null);
        return this;
    }

    public NumberSchema positive() {
        addCheck("positive", value -> value.doubleValue() > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck("range", value -> value.doubleValue() >= min && value.doubleValue() <= max);
        return this;
    }
}
