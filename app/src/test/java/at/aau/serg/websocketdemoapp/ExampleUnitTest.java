package at.aau.serg.websocketdemoapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import at.aau.serg.websocketdemoapp.networking.WebSocketClient;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
class ExampleUnitTest {
    @Test
    void testConcatenateStringsMethod() {
        String first = "Hello";
        String second = "World";

        String result = WebSocketClient.concatenateStrings(first, second);

        assertEquals("Hello World", result);
    }
}