package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean required = false;

    @SuppressWarnings("unchecked")
    public final <S extends BaseSchema<T>> S required() {
        this.required = true;
        return (S) this;
    }

    public abstract boolean isValid(Object value);
}
