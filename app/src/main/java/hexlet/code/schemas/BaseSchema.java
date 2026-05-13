package hexlet.code.schemas;

public abstract class BaseSchema<T, S extends BaseSchema<T, S>> {
    protected boolean required = false;

    public final S required() {
        this.required = true;
        return self();
    }

    protected abstract S self();

    public abstract boolean isValid(Object value);
}
