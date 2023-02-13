package com.capexercise.auxiliary;

import com.capexercise.huffmanimplement.auxiliary.GeneralMethods;
import com.capexercise.huffmanimplement.auxiliary.IGeneralMethods;
import com.capexercise.huffmanimplement.auxiliary.TreeNode;
import com.capexercise.huffmanimplement.auxiliary.IFileContents;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GeneralMethodsTest {
    TreeNode child1, child2, root;
    IGeneralMethods testRef;
    Map<Character, Integer> freqMap;
    Map<Character, String> codeMap;

    File fileObj;


    @Before
    public void setup() {
        fileObj = new File("src/textFiles/nonExistentFile.txt");

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

    @Test(expected = RuntimeException.class)
    public void testExtractContentsFail() {
        testRef.extractContents(fileObj);
    }

    @Test(expected = RuntimeException.class)
    public void testExtractContentsEmpty() {
        fileObj = new File("src/textFiles/compressedFiles/empty.txt");
        testRef.extractContents(fileObj);
    }

    @Test
    public void testExtractContents(){
        fileObj = new File("src/textFiles/compressedFiles/testCompressed.txt");

        IFileContents fileContents = testRef.extractContents(fileObj);

        assertEquals("$,97,#,#,98,#,#",fileContents.getHuffTree());
        assertEquals(3,fileContents.getExtraBits());

        byte[] expectedByteArray = {82,120};

        assertTrue(expectedByteArray.length == fileContents.getByteArray().length);

        boolean flag = true;

        for(int i=0;i<expectedByteArray.length;i++){
            if(expectedByteArray[i] != fileContents.getByteArray()[i]){
                flag = false;
                break;
            }
        }

        assertTrue(flag);

    }


}