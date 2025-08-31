package gloqi.command;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gloqi.ui.GloqiException;

public class CommandParserTest {
    @Test
    public void commandParser_invalidCommand_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("any any any"));
        assertEquals("""
                Invalid command, only following commands are supported:
                list,mark,unmark,bye,deadline,event,todo""", exception.getMessage());
    }

    @Test
    public void getShowArg_validInput_success() throws GloqiException {
        assertEquals(LocalDate.parse("2019-05-15"), new CommandParser("show 2019-05-15").getDateArg());
    }

    @Test
    public void getShowArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("Show"));
        assertEquals("""
                Date for the show is Missing!!!Please follow my show format:
                show <date:yyyy-MM-dd>""", exception.getMessage());
    }

    @Test
    public void getShowArg_invalidDateFormat_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("Show 2019/5/5"));
        assertEquals("""
                Date for the show is Invalid!!!Please follow my show format:
                show <date:yyyy-MM-dd>""", exception.getMessage());
    }

    @Test
    public void getIntArg_validInput_success() throws GloqiException {
        assertEquals(0, new CommandParser("mark 1").getIntArg());
    }

    @Test
    public void getIntArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("mark"));
        assertEquals("You need to tell me which task to mark/unmark/delete, cannot be empty",
                exception.getMessage());
    }

    @Test
    public void getIntArg_invalidInt_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("mark g"));
        assertEquals("you need to tell me the row number of the task you want to mark/unmark/delete",
                exception.getMessage());
    }

    @Test
    public void getIntArg_negativeNumb_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("mark -1"));
        assertEquals("your mark/unmark/delete number cannot be negative",
                exception.getMessage());
    }


    @Test
    public void getTodoArg_validInput_success() throws GloqiException {
        assertEquals(new String[]{"123"}, new CommandParser("todo 123").getStringArg());
    }

    @Test
    public void getTodoArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("todo"));
        assertEquals("""
                Description for the todo is Missing!!!Please follow my todo format:
                todo <your task>""", exception.getMessage());
    }

    @Test
    public void getDeadlineArg_validInput_success() throws GloqiException {
        assertArrayEquals(new String[]{"123", "2019-05-15"}, new CommandParser("deadline 123 /by 2019-05-15")
                .getStringArg());
    }

    @Test
    public void getDeadlineArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("deadline"));
        assertEquals("""
                Description for the task is Missing!!!Please follow my deadline format:
                deadline <your task> /by <date>""", exception.getMessage());
    }

    @Test
    public void getDeadlineArg_noKeyword_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("deadline asdas"));
        assertEquals("""
                Wrong!!! I cannot find '/by' keyword. Please follow my deadline format:
                deadline <your task> /by <date>""", exception.getMessage());
    }

    @Test
    public void getDeadlineArg_noDate_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("deadline asdas /by"));
        assertEquals("""
                Wrong!!! no Date after '/by' keyword. Please follow my deadline format:
                deadline <your task> /by <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_validInput_success() throws GloqiException {
        assertArrayEquals(new String[]{"123", "2019-05-15 0600", "2019-05-15 1800"},
                new CommandParser("event 123 /from 2019-05-15 0600 /to 2019-05-15 1800").getStringArg());
    }

    @Test
    public void getEventArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("event"));
        assertEquals("""
                Description for the task is Missing!!!Please follow my Event format:
                event <your task> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noFromKeyword_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("event awdsad"));
        assertEquals("""
                Wrong!!! I cannot find '/from' keyword. Please follow my Event format:
                event <your task> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noDescription_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("event /from"));
        assertEquals("""
                Description for the task is Missing!!!Please follow my Event format:
                event <your task> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noDateAfterFrom_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("event asda /from"));
        assertEquals("""
                Wrong!!! Date is Missing after '/from'. Please follow my Event format:
                event <your task> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noToKeyword_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("event asda /from 2019-05-15 0600"));
        assertEquals("""
                Wrong!!! I cannot find '/to' keyword. Please follow my Event format:
                event <your task> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noDateBeforeTo_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("event asda /from /to"));
        assertEquals("""
                Wrong!!! Date is Missing after '/from'. Please follow my Event format:
                event <your task> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noDateAfterTo_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class,
                () -> new CommandParser("event asda /from 2019-05-15 0600 /to"));
        assertEquals("""
                Wrong!!! Date is Missing after '/to'. Please follow my Event format:
                event <your task> /from <date> /to <date>""", exception.getMessage());
    }
}
