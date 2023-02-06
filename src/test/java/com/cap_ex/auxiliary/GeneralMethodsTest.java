package com.cap_ex.auxiliary;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GeneralMethodsTest {
    TreeNode child1, child2, root;
    IGeneralMethods testRef;
    Map<Character, Integer> freqMap;
    Map<Character, String> codeMap;


    @Before
    public void setup() {
        child1 = new TreeNode('a', 1, null, null);
        child2 = new TreeNode('b', 2, null, null);
        root = new TreeNode('$', child1.getFreq() + child2.getFreq(), child1, child2);
        freqMap = new HashMap<>();
        codeMap = new HashMap<>();
        testRef = new GeneralMethods();

    }
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

    @Test(expected = ArithmeticException.class)
    public void getBinaryFromIntNegative() throws Exception{
        int input = -20;
        String actual = testRef.getBinaryFromInt(input);
    }

    @Test
    public void testSerializeForNull(){
        String actual = testRef.serialize(null);
        assertEquals(null,actual);
    }
    @Test
    public void testSerialize() {
        String actual = testRef.serialize(root);
        String expected = "$,97,#,#,98,#,#";
        assertEquals(expected,actual);
    }

    @Test
    public void testGetArraySizeZero(){
        int actaul = testRef.getArraySize(codeMap,freqMap);
        assertEquals(0,actaul);
    }

    @Test
    public void testGetArraySize(){
        freqMap.put('a',2);
        freqMap.put('b',3);

        codeMap.put('a',"0");
        codeMap.put('b',"1");

        int actaul = testRef.getArraySize(codeMap,freqMap);
        int expected = 1;
        assertEquals(expected, actaul);
    }
}