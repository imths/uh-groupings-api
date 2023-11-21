package edu.hawaii.its.api.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenericServiceResultTest {
    GenericServiceResult genericServiceResult;
    String string = "STRING";
    Integer integer = 300;
    List<Float> floats = Arrays.asList(1.0f, 1.1f, 1.2f);

    @BeforeEach
    public void setup() {
        genericServiceResult = new GenericServiceResult(Arrays.asList("string", "integer", "floats"),
                this.string, this.integer, this.floats);
    }

    @Test
    public void construction() {
        GroupingsServiceResult groupingsServiceResult = new GroupingsServiceResult("TEST", "adding single objects");

        GenericServiceResult gsr = new GenericServiceResult("floats", this.floats);
        assertNotNull(gsr);

        assertNotNull(genericServiceResult);
        // Check what was added on setup.
        constructionHelper(genericServiceResult);

        genericServiceResult.add("groupingsServiceResult", groupingsServiceResult);
        assertEquals(groupingsServiceResult, genericServiceResult.get("groupingsServiceResult"));
        assertTrue(checkIndices(3, "groupingsServiceResult"));

        constructionHelper(genericServiceResult);

        genericServiceResult.add(Arrays.asList("this", "isTest"), genericServiceResult, true);
        assertEquals(genericServiceResult, genericServiceResult.get("this"));
        assertEquals(true, genericServiceResult.get("isTest"));
        assertTrue(checkIndices(4, "this"));
        assertTrue(checkIndices(5, "isTest"));

        constructionHelper(genericServiceResult);
    }

    @Test
    public void addTest() {
        this.genericServiceResult.add("boolean", true);
        assertEquals(true, this.genericServiceResult.get("boolean"));
    }

    @Test
    public void getDataTest() {
        genericServiceResult = new GenericServiceResult(Arrays.asList("string", "integer", "floats"),
                this.string, this.integer, this.floats);

        List<Object> data = genericServiceResult.getData();
        assertEquals(string, data.get(0));
        assertEquals(integer, data.get(1));
        assertEquals(floats, data.get(2));

    }

    @Test
    public void toStringTest() {
        assertEquals("[ floats: [1.0, 1.1, 1.2]; string: STRING; integer: 300;  ]",
                this.genericServiceResult.toString());
    }

    /**
     * Check if added objects are accessible by string value.
     */
    private void constructionHelper(GenericServiceResult genericServiceResult) {

        assertEquals(this.string, genericServiceResult.get("string"));
        assertEquals(this.integer, genericServiceResult.get("integer"));
        assertEquals(this.floats, genericServiceResult.get("floats"));

        assertTrue(checkIndices(0, "string"));
        assertTrue(checkIndices(1, "integer"));
        assertTrue(checkIndices(2, "floats"));
    }

    /**
     * Check if the set S of objects added, where S(0 = "1stObjectAdded, ... n = "lastObjectAdded").
     */
    private boolean checkIndices(int i, String key) {
        return i == genericServiceResult.getMap().get(key);
    }
}
