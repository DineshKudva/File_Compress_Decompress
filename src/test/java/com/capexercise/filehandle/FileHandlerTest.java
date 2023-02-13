package com.capexercise.filehandle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileHandlerTest {
    IData_Handle testRef;

    @Before
    public void setup(){
        testRef = new FileHandler("src/textFiles/empty.txt");
    }

    @Test
    public void testReadContentForEmpty(){
        String actual = testRef.readContent().toString();
        String expected = "";
        assertEquals(expected,actual);
    }

    @Test
    public void testReadContent() {
        testRef = new FileHandler("src/textFiles/testFile.txt");
        String actual = testRef.readContent().toString();
        String expected = "ababaabaabbbb";
        assertEquals(expected,actual);
    }
}