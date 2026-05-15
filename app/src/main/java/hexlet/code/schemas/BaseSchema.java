package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Базовый класс для схем валидаторов.
 *
 * @param <T> тип значения проверяется схемой.
 */
public abstract class BaseSchema<T> {
    protected Map<String, Predicate<T>> checks = new HashMap<>();
    protected boolean required = false;

    protected final void addCheck(String name, Predicate<T> validate) {
        checks.put(name, validate);
    }

    /**
     * Отмечает значение, соответствующее текущей схеме.
     *
     * <p>Подклассы могут переопределять этот метод только для того,
     *      чтобы сузить тип возвращаемого значения для гибкого API.
     * Подобные переопределения должны вызывать {@code super.required()}
     * и сохранить то же самое поведение.</p>
     *
     * @return текущий экземпляр схемы
     */
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
