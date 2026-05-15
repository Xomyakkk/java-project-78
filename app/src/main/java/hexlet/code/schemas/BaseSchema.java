package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected Map<String, Predicate<T>> checks = new HashMap<>();
    protected boolean required = false;

    protected final void addCheck(String name, Predicate<T> validate) {
        checks.put(name, validate);
    }

    public BaseSchema<T> required() {
        required = true;
        return this;
    }

    public final boolean isValid(T value) {
        if (value == null) {
            return !required;
        }

        return checks.values().stream().allMatch(check -> check.test(value));
    }
}
