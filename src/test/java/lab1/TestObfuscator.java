package lab1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestObfuscator {
    @Test
    public void testObfuscate() {
        String testStr = "abcghjggk";
        String result = Obfuscator.obfuscate(testStr);
        assertNotEquals(result, testStr);
    }

    @Test
    public void testUnObfuscate() {
        String testStr = "abcghjggk";
        String obfuscated = Obfuscator.obfuscate(testStr);
        String result = Obfuscator.unobfuscate(obfuscated);
        assertEquals(result, testStr);
    }
}
