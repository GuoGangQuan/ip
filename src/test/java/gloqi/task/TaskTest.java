package gloqi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    public void markDone_stringDisplay_success() {
        assertEquals("[x] gg", new Task("gg").markDone(true).toString());
    }

    @Test
    public void saveFormat_saveString_success() {
        assertEquals("2|gg| ", new Task("gg").saveFormat());
    }

    @Test
    public void compareDate_returnType_success() {
        assertFalse(new Task("gg").compareDate(LocalDate.now()));
    }
}
