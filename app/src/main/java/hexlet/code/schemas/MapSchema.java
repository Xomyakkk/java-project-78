package hexlet.code.schemas;

import java.util.Map;
import java.util.HashMap;

/**
 * Схема для проверки объектов {@link Map}.
 *
 * <p>Позволяет задать ограничения:
 * <ul>
 *   <li>{@code required} – обязательность значения (унаследовано от {@link BaseSchema})</li>
 *   <li>{@code size} – точное число элементов в карте</li>
 *   <li>{@code shape} – схемы для проверки значений каждого ключа</li>
 * </ul></p>
 *
 * @author hexlet
 */
public final class MapSchema extends BaseSchema<Map<?, ?>> {
    @Override
    public MapSchema required() {
        super.required();
        return this;
    }

    private Integer size = null;
    private Map<String, BaseSchema<?>> schemas = new HashMap<>();

    public MapSchema sizeof(int mapSize) {
        this.size = mapSize;
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> shapeSchemas) {
        this.schemas = new HashMap<>(shapeSchemas);
        return this;
    }

    /**
     * Проверяет валидность переданного значения.
     *
     * <p>Сложные места:</p>
     * <ul>
     *   <li>{@code value == null} – проверка на {@code null} должна учитывать свойство {@code required}.
     *   Это важная часть, чтобы различать "отсутствие" и "необязательное присутствие".</li>
     *   <li>Преобразование {@code value} в {@link Map} без проверки типа.
     *   Здесь предполагается, что метод вызывается только с правильным типом,
     *   но если это не так – произойдет {@code ClassCastException}.
     *   Это потенциальный риск при использовании схемы в более общем контексте.</li>
     *   <li>Проверка размера: {@code size != null && map.size() != size} –
     *   здесь два условия объединены в одну проверку, чтобы избежать лишних вычислений,
     *   но это делает строку немного сложнее для восприятия.</li>
     * </ul>
     *
     * @param value объект, который нужно проверить
     * @return {@code true} если значение удовлетворяет схеме, иначе {@code false}
     */
    @Override
    public boolean isValid(Object value) {
        // Проверка на null с учётом обязательности
        if (value == null) {
            return !required;
        }

        // Преобразуем объект к Map. Предполагается корректный тип.
        Map<?, ?> map = (Map<?, ?>) value;

        // Если задан размер, проверяем его соответствие
        if (size != null && map.size() != size) {
            return false;
        }

        // Проверяем значения по вложенным схемам
        for (Map.Entry<String, BaseSchema<?>> entry : schemas.entrySet()) {
            String key = entry.getKey();
            BaseSchema<?> schema = entry.getValue();
            Object mapValue = map.get(key);

            if (!schema.isValid(mapValue)) {
                return false;
            }
        }

        // Все проверки пройдены – значение валидно
        return true;
    }
}
