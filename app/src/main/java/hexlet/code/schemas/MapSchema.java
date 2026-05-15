package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    private Map<String, BaseSchema<?>> schemas = new HashMap<>();

    @SuppressWarnings("unchecked")
    private static boolean isValueValid(BaseSchema<?> schema, Object value) {
        return ((BaseSchema<Object>) schema).isValid(value);
    }

    @Override
    public MapSchema required() {
        super.required();
        addCheck("required", value -> value != null);
        return this;
    }

    public MapSchema sizeof(int mapSize) {
        addCheck("sizeof", value -> value.size() == mapSize);
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> shapeSchemas) {
        schemas = new HashMap<>(shapeSchemas);
        addCheck("shape", value -> schemas.entrySet().stream()
            .allMatch(entry -> isValueValid(entry.getValue(), value.get(entry.getKey()))));
        return this;
    }
}
