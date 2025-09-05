package gloqi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    public void markDone_stringDisplay_success() {
        assertEquals("[x] gg", new Task("gg").setDone(true).toString());
    }

    @Test
    public void compareDate_returnType_success() {
        assertFalse(new Task("gg").compareDate(LocalDate.now()));
    }
}
