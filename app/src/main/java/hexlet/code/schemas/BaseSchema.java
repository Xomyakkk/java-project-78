package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean required = false;

    public T required() {
        required = true;
        return (T) this;
    }

    public abstract boolean isValid(Object value);
}
