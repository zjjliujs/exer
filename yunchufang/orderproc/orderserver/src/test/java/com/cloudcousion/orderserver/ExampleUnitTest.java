package com.cloudcousion.orderserver;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void uuid_test() {
        UUID uuid = UUID.fromString("4f304b59-6634-4558-a128-a8ce12b1f818");
        assertEquals(uuid.toString(), "4f304b59-6634-4558-a128-a8ce12b1f818");
    }
}