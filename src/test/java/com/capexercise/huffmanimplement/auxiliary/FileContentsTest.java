package com.capexercise.huffmanimplement.auxiliary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileContentsTest {

    IFileContents testRef;

    @Before
    public void setup(){
        String huffTree = "$,97,#,#,98,#,#";
        int extraBits = 3;
        byte[] byteArray = new byte[]{97,98,97,98};
        testRef = new FileContents(huffTree,extraBits,byteArray);
    }

    @Test
    public void testGetHuffTree() {
        String actual = testRef.getHuffTree();
        String expected = "$,97,#,#,98,#,#";
        assertEquals(expected,actual);
    }

    @Test
    public void testGetExtraBits() {
        int actual = testRef.getExtraBits();
        int expected = 3;
        assertEquals(expected,actual);
    }

    @Test
    public void testGetByteArray() {
        byte[] actual = testRef.getByteArray();
        byte[] expected = new byte[]{97,98,97,98};

        assertTrue(actual.length == expected.length);

        boolean flag = true;

        for(int i=0;i<actual.length;i++){
            if(actual[i] != expected[i]){
                flag = false;
                break;
            }
        }

        assertTrue(flag);
    }

    @Test
    public void testSetHuffTree() {
        testRef.setHuffTree("$,98,#");
        String actual = testRef.getHuffTree();
        String expected = "$,98,#";
        assertEquals(expected,actual);
    }

    @Test
    public void testSetExtraBits() {
        testRef.setExtraBits(5);
        int actual = testRef.getExtraBits();
        int expected = 5;
        assertEquals(expected,actual);
    }

    @Test
    public void testSetByteArray() {
        testRef.setByteArray(new byte[]{52,110,98,34});
        byte[] actual = testRef.getByteArray();
        byte[] expected = new byte[]{52,110,98,34};

        assertTrue(actual.length == expected.length);

        boolean flag = true;

        for(int i=0;i<actual.length;i++){
            if(actual[i] != expected[i]){
                flag = false;
                break;
            }
        }

        assertTrue(flag);
    }
}