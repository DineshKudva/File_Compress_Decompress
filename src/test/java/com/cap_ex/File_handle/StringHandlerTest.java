package com.cap_ex.File_handle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringHandlerTest {
    IData_Handle testRef;

    @Before
    public void setup(){
        testRef = new StringHandler("");
    }
    @Test
    public void testReadContentForEmptyString() {
        String actual = testRef.readContent().toString();
        String expected = "";
        assertEquals(expected,actual);
    }

    @Test
    public void testReadContentForStringInput() {
        testRef = new StringHandler("abbabababbbababa");
        String actual = testRef.readContent().toString();
        String expected = "abbabababbbababa";
        assertEquals(expected,actual);
    }

    @Test
    public void testReadContentForEmptyByteArray() {
        testRef = new StringHandler(new byte[]{});
        String actual = testRef.readContent().toString();
        String expected = "";
        assertEquals(expected, actual);
    }
    @Test
    public void testReadContentForByteArray() {
        String str = "hello world";
        byte[] input = str.getBytes();
        testRef = new StringHandler(input);

        String actual = testRef.readContent().toString();
        String expected = "hello world";

        assertEquals(expected,actual);
    }
}