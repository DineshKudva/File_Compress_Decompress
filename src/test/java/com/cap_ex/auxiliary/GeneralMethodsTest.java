package com.cap_ex.auxiliary;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneralMethodsTest {
    IGeneralMethods testRef = new GeneralMethods();
    @Test
    public void testGetAscii(){
        String input = "01100010";
        char actual = testRef.getAscii(input);
        char expected = 'b';
        assertEquals("check obtained ascii value",expected,actual);
    }

    @Test
    public void testGetBinaryFromChar() {
        char input = 'b';
        String actual = testRef.getBinaryFromChar(input);
        String expected = "01100010";
        assertEquals("check obtained binary string (from a character)",expected,actual);
    }

    @Test
    public void testGetBinaryFromInt() {
        int input = 98;
        String actual = testRef.getBinaryFromInt(input);
        String expected = "01100010";
        assertEquals("check obtained binary string (from an integer)",expected,actual);
    }
}