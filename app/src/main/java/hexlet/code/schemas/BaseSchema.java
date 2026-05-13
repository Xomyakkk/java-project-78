package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean required = false;

    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    public abstract boolean isValid(Object value);
}
