package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean required = false;

    /**
     * Marks the current schema as requiring a non-null value.
     *
     * <p>Concrete schemas may override this method to narrow the return
     * type for fluent chaining while preserving the same validation behavior.</p>
     *
     * @return current schema instance
     */
    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    public abstract boolean isValid(Object value);
}
