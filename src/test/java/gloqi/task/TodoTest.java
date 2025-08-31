package gloqi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {
    @Test
    public void toString_stringDisplay_success() {
        assertEquals("[T][ ] gg", new Todo("gg").toString());
    }

}
