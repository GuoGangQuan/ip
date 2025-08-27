package gloqi.command;

import gloqi.ui.GloqiException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class CommandParserTest {
    @Test
    public void CommandParser_unknownCommand_exceptionThrow() throws GloqiException {
        assertThrows(GloqiException.class, () -> {
            new CommandParser("any any any");
        });
    }

}
