package com.cap_ex.decompression;

import com.cap_ex.auxiliary.IGeneralMethods;
import com.cap_ex.auxiliary.TreeNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.util.Stack;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HuffmanDecompressorTest {
    IHuffmanDecompress testRef;

    IGeneralMethods method;

    TreeNode root;


    @Before
    public void setup(){

        method = Mockito.mock(IGeneralMethods.class);
        testRef = new HuffmanDecompressor(method);

        root = new TreeNode('$',13,null,null);
        root.setLeft(new TreeNode('a',6,null,null));
        root.setRight(new TreeNode('b',7,null,null));
    }


    @Test
    public void testGetBinaryDataForEmpty(){
        byte[] input = new byte[]{};
        String expected = "";
        String actual = testRef.getBinaryData(input);
        assertEquals("result must be empty!!",expected,actual);
    }

    @Test
    public void testGetBinaryData(){

        Mockito.when(method.getBinaryFromInt(82)).thenReturn("01010010");
        Mockito.when(method.getBinaryFromInt(120)).thenReturn("01111000");


        byte[] byteArray = {82,120};
        String expected = "0101001001111000";
        String actual = testRef.getBinaryData(byteArray);

        assertEquals("binary string returned does the not match the expected result",expected,actual);
    }

    @Test
    public void testGetBinaryDataForNegativeBytes(){

        Mockito.when(method.getBinaryFromInt(52)).thenReturn("00110100");
        Mockito.when(method.getBinaryFromInt(131)).thenReturn("10000011");


        byte[] byteArray = {52,-125};
        String expected = "0011010010000011";
        String actual = testRef.getBinaryData(byteArray);

        assertEquals("binary string returned does the not match the expected result",expected,actual);
    }

    @Test
    public void dataDecompression() {
        String binaryCode = "10101000";
        int extraBits = 1;

        String resultPath = testRef.dataDecompression(binaryCode,root,extraBits);
        String actual = "";
        FileReader fr = null;
        try {
            fr = new FileReader(new File(resultPath));
            int val = fr.read();
            while(val!=-1){
                actual += (char) val;
                val = fr.read();
            }
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }


        String expected = "bababaa";

        assertEquals(expected,actual);
    }

    @Test
    public void testDeserializeForNull(){
        String input = null;
        TreeNode actual = testRef.deserialize(input);
        assertEquals(null,actual);
    }

    @Test
    public void testDeserializeForEmpty(){
        String input = "";
        TreeNode actual = testRef.deserialize(input);
        assertEquals(null,actual);
    }

    @Test
    public void testDeserialize() {

        String input = "$,97,#,#,98,#,#";

        IHuffmanDecompress testSpy = Mockito.spy(testRef);

        Mockito.doReturn(root).when(testSpy).treeBuilder(input.split(","));


        TreeNode newRoot = testSpy.deserialize(input);

        Stack<TreeNode> nodeStack = new Stack<>();
        String expected = "";
        nodeStack.push(root);
        while(!nodeStack.empty()){
            TreeNode temp = nodeStack.pop();
            if(temp == null)
                continue;
            expected += temp.getChar();
            nodeStack.push(temp.getRightChild());
            nodeStack.push(temp.getLeftChild());
        }

        String actual = "";
        nodeStack.push(newRoot);
        while(!nodeStack.empty()){
            TreeNode temp = nodeStack.pop();
            if(temp == null)
                continue;
            actual += temp.getChar();
            nodeStack.push(temp.getRightChild());
            nodeStack.push(temp.getLeftChild());
        }

        assertEquals("Trees formed must be correct",expected,actual);

    }

    @Test
    public void testTreeBuilder() {
        testRef = new HuffmanDecompressor();
        String[] input = ("$,97,#,#,98,#,#").split(",");

        TreeNode newRoot = testRef.treeBuilder(input);
        Stack<TreeNode> nodeStack = new Stack<>();
        String expected = "";
        nodeStack.push(root);
        while(!nodeStack.empty()){
            TreeNode temp = nodeStack.pop();
            if(temp == null)
                continue;
            expected += temp.getChar();
            nodeStack.push(temp.getRightChild());
            nodeStack.push(temp.getLeftChild());
        }

        String actual = "";
        nodeStack.push(newRoot);
        while(!nodeStack.empty()){
            TreeNode temp = nodeStack.pop();
            if(temp == null)
                continue;
            actual += temp.getChar();
            nodeStack.push(temp.getRightChild());
            nodeStack.push(temp.getLeftChild());
        }

        assertEquals("Trees formed must be correct",expected,actual);

    }
}