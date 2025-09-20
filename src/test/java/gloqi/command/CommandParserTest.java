package gloqi.command;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gloqi.ui.GloqiException;

public class CommandParserTest {
    @Test
    public void commandParser_invalidCommand_success() throws GloqiException {
        assertEquals(Command.INVALID, new CommandParser("any any any").getCmd());
    }

    @Test
    public void commandParser_listCommand_success() throws GloqiException {
        assertEquals(Command.LIST, new CommandParser("list").getCmd());
    }

    @Test
    public void commandParser_unmarkCommand_success() throws GloqiException {
        CommandParser parser = new CommandParser("unmark 1");
        assertEquals(Command.UNMARK, parser.getCmd());
        assertArrayEquals(new int[]{0}, parser.getIntArg());
    }

    @Test
    public void commandParser_byeCommand_success() throws GloqiException {
        assertEquals(Command.BYE, new CommandParser("bye").getCmd());
    }

    @Test
    public void commandParser_deleteCommand_success() throws GloqiException {
        CommandParser parser = new CommandParser("delete 1,2");
        assertEquals(Command.DELETE, parser.getCmd());
        assertArrayEquals(new int[]{0, 1}, parser.getIntArg());
    }

    @Test
    public void commandParser_findCommand_success() throws GloqiException {
        CommandParser parser = new CommandParser("find star");
        assertEquals(Command.FIND, parser.getCmd());
        assertArrayEquals(new String[]{"star"}, parser.getStringArg());
    }

    @Test
    public void getShowArg_validInput_success() throws GloqiException {
        assertEquals(LocalDate.parse("2019-05-15"), new CommandParser("show 2019-05-15").getDateArg());
    }

    @Test
    public void getShowArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "Show"));
        assertEquals("""
                Missing argument!!! Please follow following format:
                show <date>""", exception.getMessage());
    }

    @Test
    public void getShowArg_invalidDateFormat_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "Show 2019/5/5"));
        assertEquals("""
                Date for the show is Invalid!!!Please follow my show format:
                show <yyyy-MM-dd>""", exception.getMessage());
    }

    @Test
    public void getIntArg_validInput_success() throws GloqiException {
        assertEquals(0, new CommandParser("mark 1").getIntArg()[0]);
    }

    @Test
    public void getIntArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("mark"));
        assertEquals("Missing argument!!! Please follow following format:\nmark/unmark <Task Number>",
                exception.getMessage());
    }

    @Test
    public void getIntArg_invalidInt_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("mark g"));
        assertEquals("Invalid index!!!Please provide a number.\n",
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
        assertArrayEquals(new String[]{"123"}, new CommandParser("todo 123").getStringArg());
    }

    @Test
    public void getTodoArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("todo"));
        assertEquals("""
                Missing argument!!! Please follow following format:
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
                Missing argument!!! Please follow following format:
                deadline <task description> /by <date>""", exception.getMessage());
    }

    @Test
    public void getDeadlineArg_noKeyword_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "deadline asdas"));
        assertEquals("""
                Missing argument!!! Please follow following format:
                deadline <task description> /by <date>""", exception.getMessage());
    }

    @Test
    public void getDeadlineArg_noDate_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "deadline asdas /by"));
        assertEquals("""
                Missing argument '/by Date'!!! Please follow following format:
                deadline <task description> /by <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_validInput_success() throws GloqiException {
        assertArrayEquals(new String[]{"123", "2019-05-15 0600", "2019-05-15 1800"},
                new CommandParser("event 123 /from 2019-05-15 0600 /to 2019-05-15 1800").getStringArg());
    }

    @Test
    public void getEventArg_noArg_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "event"));
        assertEquals("""
                Missing argument!!! Please follow following format:
                event <task description> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noFromKeyword_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "event awdsad"));
        assertEquals("""
                Missing argument!!! Please follow following format:
                event <task description> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noDescription_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser("event /from"));
        assertEquals("""
                Missing argument 'task description'!!! Please follow following format:
                event <task description> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noDateAfterFrom_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "event asda /from"));
        assertEquals("""
                Missing argument!!! Please follow following format:
                event <task description> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noToKeyword_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "event asda /from 2019-05-15 0600"));
        assertEquals("""
                Missing argument!!! Please follow following format:
                event <task description> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noDateBeforeTo_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "event asda /from /to"));
        assertEquals("""
                Missing argument '/from Date'!!! Please follow following format:
                event <task description> /from <date> /to <date>""", exception.getMessage());
    }

    @Test
    public void getEventArg_noDateAfterTo_exceptionThrow() {
        GloqiException exception = assertThrows(GloqiException.class, () -> new CommandParser(
                "event asda /from 2019-05-15 0600 /to"));
        assertEquals("""
                Missing argument '/to Date'!!! Please follow following format:
                event <task description> /from <date> /to <date>""", exception.getMessage());
    }
}
