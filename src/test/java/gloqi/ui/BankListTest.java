package gloqi.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gloqi.task.Deadline;
import gloqi.task.Todo;

public class BankListTest {
    @Test
    public void addTask_validInput_success() throws GloqiException {
        assertEquals("Got it. I've added this task:\n[T][ ] read book\nNow you have 1 tasks in the bank.",
                new BankList(new DataManager("data/data.txt")).addTask(new Todo("read book")));
    }

    @Test
    public void markTask_validInput_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        assertEquals("Nice! I've marked this task as done:\n[T][x] read book", bankList.markTask(0));
    }

    @Test
    public void markTask_outOfRangeInput_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        GloqiException exception = assertThrows(GloqiException.class, () -> bankList.markTask(9));
        assertEquals("Task index 10 is out of range", exception.getMessage());
    }

    @Test
    public void markTask_negativeInput_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        GloqiException exception = assertThrows(GloqiException.class, () -> bankList.markTask(-2));
        assertEquals("Task index -1 is out of range", exception.getMessage());
    }

    @Test
    public void unmarkTask_validInput_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        assertEquals("OK, I've marked this task as not done yet:\n[T][ ] read book",
                bankList.unmarkTask(0));
    }

    @Test
    public void deleteTask_validInput_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        String expected = """
                Tasks have been deleted:
                [T][ ] read book\n
                Now you have 0 tasks in the bank.""";
        assertEquals(expected,
                bankList.deleteTask(new int[]{0}));
    }

    @Test
    public void printList_validInput_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        assertEquals("1. [T][ ] read book", bankList.printList());
    }

    @Test
    public void printList_validInputWithDate_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Deadline(new String[]{"read book", "2024-12-12 1800"}));
        LocalDate date = LocalDate.of(2024, 12, 12);
        String expected = "Tasks found on date: Dec 12 2024\n1. [D][ ] read book (by: Dec 12 2024 6 pm)";
        assertEquals(expected, bankList.printList(date));
    }

    @Test
    public void printList_validInputWithDateNoMatch_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Deadline(new String[]{"read book", "2024-12-12 1800"}));
        LocalDate date = LocalDate.of(2024, 12, 13);
        String expected = "No tasks found on date: Dec 13 2024";
        assertEquals(expected, bankList.printList(date));
    }

    @Test
    public void findTask_validInput_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        assertEquals("1. [T][ ] read book", bankList.findTask("book"));
    }

    @Test
    public void findTask_validInputNoMatch_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        assertEquals("No matching tasks found for: xx", bankList.findTask("xx"));
    }

    @Test
    public void loadBankList_validStorageFile_success() throws GloqiException {
        BankList bankList = new BankList(new DataManager("data/data.txt"));
        bankList.addTask(new Todo("read book"));
        assertEquals(bankList.printList(), bankList.loadBankList().printList());
    }
}
