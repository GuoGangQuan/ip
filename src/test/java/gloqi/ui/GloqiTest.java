package gloqi.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class GloqiTest {
    @Test
    public void initialize_invalidStorage_success(@TempDir Path tmp) throws GloqiException {
        Gloqi gloqi = new Gloqi();
        Path file = tmp.resolve("gloqi.txt");
        String response = gloqi.initialize(file.toString());
        assertEquals("No data file found!\nStart up with a fresh file!", response);
    }
    @Test
    public void initialize_validStorage_success(@TempDir Path tmp) {
        Gloqi gloqi = new Gloqi();
        Path file = tmp.resolve("gloqi.txt");
        gloqi.initialize(file.toString());
        gloqi.run("todo Read book");
        String response = gloqi.initialize(file.toString());
        assertEquals("Tasks are loaded successfully!", response);
    }

    @Test
    public void getGreeting_success() {
        Gloqi gloqi = new Gloqi();
        String response = gloqi.getGreeting();
        assertEquals("Hello I am Gloqi\nhow can i help you", response);
    }

    @Test
    public void run_validList_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        gloqi.run("todo Read book");
        String response = gloqi.run("List");
        assertEquals("1. [T][ ] Read book", response);
    }

    @Test
    public void run_validMark_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        gloqi.run("todo Read book");
        String response = gloqi.run("mark 1");
        assertEquals("Nice! I've marked this task as done:\n[T][x] Read book", response);
    }

    @Test
    public void run_invalidMark_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        String response = gloqi.run("mark 999");
        assertEquals("Task index 999 is out of range", response);
    }

    @Test
    public void run_validUnmark_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        gloqi.run("todo Read book");
        String response = gloqi.run("unmark 1");
        assertEquals("OK, I've marked this task as not done yet:\n[T][ ] Read book", response);
    }

    @Test
    public void run_validDeadline_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        String response = gloqi.run("Deadline Submit assignment /by 2024-09-30 2359");
        String expected = """
                Got it. I've added this task:
                [D][ ] Submit assignment (by: Sep 30 2024 11 pm)
                Now you have 1 tasks in the bank.""";
        assertEquals(expected, response);
    }

    @Test
    public void run_validEvent_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        String response = gloqi.run("Event Team meeting /from 2024-10-01 1400 /to 2024-10-01 1500");
        String expected = """
                Got it. I've added this task:
                [E][ ] Team meeting (from: Oct 01 2024 2 pm to: Oct 01 2024 3 pm)
                Now you have 1 tasks in the bank.""";
        assertEquals(expected, response);
    }

    @Test
    public void run_validDelete_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        gloqi.run("todo Read book");
        String response = gloqi.run("delete 1");
        String expected = """
                Tasks have been deleted:
                [T][ ] Read book\n
                Now you have 0 tasks in the bank.""";
        assertEquals(expected, response);
    }

    @Test
    public void run_validShow_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        gloqi.run("Deadline Submit assignment /by 2024-09-30 2359");
        String response = gloqi.run("show 2024-09-30");
        String expected = """
                Tasks found on date: Sep 30 2024
                1. [D][ ] Submit assignment (by: Sep 30 2024 11 pm)""";
        assertEquals(expected, response);
    }

    @Test
    public void run_validFind_success(@TempDir Path tmp) {
        Gloqi gloqi = testFileInistalize(tmp);
        gloqi.run("Deadline Submit assignment /by 2024-09-30 2359");
        String response = gloqi.run("find assignment");
        String expected = """
                1. [D][ ] Submit assignment (by: Sep 30 2024 11 pm)""";
        assertEquals(expected, response);
    }

    @Test
    public void run_validBye_success() {
        Gloqi gloqi = new Gloqi();
        String response = gloqi.run("bye");
        String expected = "Bye, see you next time!";
        assertEquals(expected, response);
    }

    @Test
    public void run_invalidCommand_success() {
        Gloqi gloqi = new Gloqi();
        String response = gloqi.run("bye1231");
        String expected = """
                Invalid command, only following commands are supported:
                list, mark, unmark, bye, deadline, event, todo, show, delete, find""";
        assertEquals(expected, response);
    }

    private Gloqi testFileInistalize(@TempDir Path tmp) {
        Gloqi gloqi = new Gloqi();
        Path file = tmp.resolve("gloqi.txt");
        gloqi.initialize(file.toString());
        return gloqi;
    }
}
