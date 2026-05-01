package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
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
}
