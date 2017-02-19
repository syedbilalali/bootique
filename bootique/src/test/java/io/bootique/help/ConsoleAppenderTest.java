package io.bootique.help;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsoleAppenderTest {

    // TODO: what's the point of hardcoded min line width?
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMinWidth() {
        new ConsoleAppender(new StringBuilder(), ConsoleAppender.MIN_LINE_WIDTH - 2);
    }

    @Test
    public void testPrintln() {
        StringBuilder out = new StringBuilder();
        ConsoleAppender appender = new ConsoleAppender(out, 20);

        appender.println("a", "b", "cd");
        assertEquals("abcd" + ConsoleAppender.NEWLINE, out.toString());
    }

    @Test
    public void testPrintln_Offset() {
        StringBuilder out = new StringBuilder();
        ConsoleAppender appender = new ConsoleAppender(out, 20).withOffset(3);

        appender.println("a", "b", "cd");
        assertEquals("   abcd" + ConsoleAppender.NEWLINE, out.toString());
    }

    @Test
    public void testPrintln_Long() {
        StringBuilder out = new StringBuilder();
        ConsoleAppender appender = new ConsoleAppender(out, 20);

        appender.println("012345678", "012345678", "01234567890");
        assertEquals("01234567801234567801234567890" + ConsoleAppender.NEWLINE, out.toString());
    }

    @Test
    public void testFoldPrintln() {
        StringBuilder out = new StringBuilder();
        ConsoleAppender appender = new ConsoleAppender(out, 20);

        appender.foldPrintln("a", "b", "cd");
        assertEquals("abcd" + ConsoleAppender.NEWLINE, out.toString());
    }

    @Test
    public void testFoldPrintln_Long() {
        StringBuilder out = new StringBuilder();
        ConsoleAppender appender = new ConsoleAppender(out, 20);

        appender.foldPrintln("012345", " 01234567890", " 01234567890", " 01234567890");
        assertEquals("012345 01234567890" + ConsoleAppender.NEWLINE
                + "01234567890" + ConsoleAppender.NEWLINE
                + "01234567890" + ConsoleAppender.NEWLINE, out.toString());
    }

    @Test
    public void testFoldPrintln_Long_NoSpace() {
        StringBuilder out = new StringBuilder();
        ConsoleAppender appender = new ConsoleAppender(out, 20);

        appender.foldPrintln("012345", "01234567890", "01234567890", "01234567890");
        assertEquals("01234501234567890012" + ConsoleAppender.NEWLINE
                + "3456789001234567890" + ConsoleAppender.NEWLINE, out.toString());
    }

    @Test
    public void testFoldPrintln_OffsetInt() {
        StringBuilder out = new StringBuilder();
        ConsoleAppender appender = new ConsoleAppender(out, 20).withOffset(3);

        appender.foldPrintln("012345", " 01234567890", " 01234567890");
        assertEquals("   012345" + ConsoleAppender.NEWLINE
                + "   01234567890" + ConsoleAppender.NEWLINE
                + "   01234567890" + ConsoleAppender.NEWLINE, out.toString());
    }

    @Test
    public void testFoldPrintln_OffsetString() {
        StringBuilder out = new StringBuilder();
        ConsoleAppender appender = new ConsoleAppender(out, 20).withOffset("xyz");

        appender.foldPrintln("012345", " 01234567890", " 01234567890");
        assertEquals("xyz012345" + ConsoleAppender.NEWLINE
                + "xyz01234567890" + ConsoleAppender.NEWLINE
                + "xyz01234567890" + ConsoleAppender.NEWLINE, out.toString());
    }

    @Test
    public void testFoldPrintln_FullLine() {
        StringBuilder out = new StringBuilder();

        // this is a magic proportion - 12 char line with a 12 char word. Used to throw out of bounds in the past
        ConsoleAppender appender = new ConsoleAppender(out, 16).withOffset(4);

        appender.foldPrintln("123456789012 1234567890123");
        assertEquals("    123456789012123456789012" + ConsoleAppender.NEWLINE
                + "    3" + ConsoleAppender.NEWLINE, out.toString());
    }
}