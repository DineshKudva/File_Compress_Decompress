package com.cap_ex.auxiliary;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GeneralMethodsTest {

    TreeNode child1 = new TreeNode('a',1,null,null);
    TreeNode child2 = new TreeNode('b',2,null,null);
    TreeNode root = new TreeNode('$',child1.getFreq()+child2.getFreq(),child1,child2);

    Map<Character,Integer> freqMap = new HashMap<>();
    Map<Character,String> codeMap = new HashMap<>();

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

    @Test
    public void testSerialize() {
        String actual = testRef.serialize(root);
        String expected = "$,97,#,#,98,#,#";
        assertEquals(expected,actual);

        actual = testRef.serialize(null);
        expected = null;
        assertEquals(null,null);
    }

    @Test
    public void testGetArraySize(){
        int actaul = testRef.getArraySize(codeMap,freqMap);
        int expected = 0;
        assertEquals(expected,actaul);

        freqMap.put('a',2);
        freqMap.put('b',3);

        codeMap.put('a',"0");
        codeMap.put('b',"1");

        actaul = testRef.getArraySize(codeMap,freqMap);
        expected = 1;
        assertEquals(expected, actaul);
    }
}