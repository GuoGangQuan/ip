package gloqi.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gloqi.ui.GloqiException;

public class DeadlineTest {
    @Test
    public void deadlineConstructor_validInput_success() throws GloqiException {
        assertEquals("[D][ ] test (by: May 06 2019 6 pm)",
                new Deadline(new String[]{"test", "2019-05-06 1800"}).toString());

    }

    @Test
    public void deadlineConstructor_invalidDate_success() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new Deadline(new String[]{"test", "2019-23-02 1800"}));
        assertEquals("your date time format is Wrong, Please follow this format: yyyy-MM-dd HHmm",
                exception.getMessage());

    }

}
