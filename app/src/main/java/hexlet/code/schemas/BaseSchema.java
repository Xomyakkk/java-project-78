package hexlet.code.schemas;

public abstract class BaseSchema<T extends BaseSchema<T>> {
    protected boolean required = false;

    @SuppressWarnings("unchecked")
    public T required() {
        required = true;
        return (T) this;
    }

    public abstract boolean isValid(Object value);
}
