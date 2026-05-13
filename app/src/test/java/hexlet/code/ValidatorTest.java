package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidatorTest {

    @Test
    public void testStringSchemaWithoutRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
    }

    @Test
    public void testStringSchemaWithRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("what does the fox say"));
    }

    @Test
    public void testStringSchemaMinLength() {
        Validator v = new Validator();
        StringSchema schema = v.string().minLength(5);

        assertFalse(schema.isValid("abc"));
        assertTrue(schema.isValid("abcde"));
    }

    @Test
    public void testStringSchemaContains() {
        Validator v = new Validator();
        StringSchema schema = v.string().contains("hex");

        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid(" Hexlet "));
    }

    @Test
    public void testStringSchemaCombined() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(5).contains("hex");

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("abc"));
        assertFalse(schema.isValid("hex"));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    public void testMinLengthOverride() {
        Validator v = new Validator();
        StringSchema schema = v.string().minLength(10).minLength(4);

        assertTrue(schema.isValid("Hexlet"));
    }

    @Test
    public void testNumberSchemaWithoutRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(null));
    }

    @Test
    public void testNumberSchemaPositiveWithoutRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number().positive();

        assertTrue(schema.isValid(null));
    }

    @Test
    public void testNumberSchemaRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number().required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(10));
    }

    @Test
    public void testNumberSchemaPositive() {
        Validator v = new Validator();
        NumberSchema schema = v.number().positive().required();

        assertFalse(schema.isValid(-10));
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(10));
    }

    @Test
    public void testNumberSchemaRange() {
        Validator v = new Validator();
        NumberSchema schema = v.number().required().range(5, 10);

        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(11));
    }

    @Test
    public void testMapSchemaWithoutRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
    }

    @Test
    public void testMapSchemaRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map().required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid(Map.of("key1", "value1")));
    }

    @Test
    public void testMapSchemaSizeof() {
        Validator v = new Validator();
        MapSchema schema = v.map().sizeof(2);

        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertFalse(schema.isValid(data));

        data.put("key2", "value2");
        assertTrue(schema.isValid(data));
    }

    @Test
    public void testMapSchemaCombined() {
        Validator v = new Validator();
        MapSchema schema = v.map().required().sizeof(2);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));

        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertFalse(schema.isValid(data));

        data.put("key2", "value2");
        assertTrue(schema.isValid(data));
    }

    @Test
    public void testMapSchemaShapeWithStringRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?, ?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required());

        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2));
    }

    @Test
    public void testMapSchemaShapeWithMinLength() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?, ?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "Anna");
        human1.put("lastName", "B");
        assertFalse(schema.isValid(human1));

        human1.put("lastName", "BB");
        assertTrue(schema.isValid(human1));
    }

    @Test
    public void testMapSchemaShapeWithNestedNumberSchema() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?, ?>> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());

        schema.shape(schemas);

        Map<String, Object> person1 = new HashMap<>();
        person1.put("name", "John");
        person1.put("age", 25);
        assertTrue(schema.isValid(person1));

        Map<String, Object> person2 = new HashMap<>();
        person2.put("name", "John");
        person2.put("age", -5);
        assertFalse(schema.isValid(person2));
    }

    @Test
    public void testMapSchemaShapeWithMissingKey() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?, ?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required());

        schema.shape(schemas);

        Map<String, String> human = new HashMap<>();
        human.put("firstName", "John");
        assertFalse(schema.isValid(human));
    }

    @Test
    public void testMapSchemaShapeCombinedWithSizeof() {
        Validator v = new Validator();
        MapSchema schema = v.map().sizeof(2);

        Map<String, BaseSchema<?, ?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required());

        schema.shape(schemas);

        Map<String, String> human = new HashMap<>();
        human.put("firstName", "John");
        human.put("lastName", "Smith");
        assertTrue(schema.isValid(human));

        human.put("middleName", "Test");
        assertFalse(schema.isValid(human));
    }
}
