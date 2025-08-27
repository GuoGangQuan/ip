package gloqi.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void toString_stringDisplay_success() {
        assertEquals("[T][ ] gg",new Todo("gg").toString());
    }
    @Test
    public void saveFormat_saveString_success() {
        assertEquals("2|gg| |T",new Todo("gg").saveFormat());
    }
}
