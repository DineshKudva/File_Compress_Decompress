package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Stack;

import static org.junit.Assert.*;

public class HuffmanDecompressorTest {
    IHuffmanDecompress testRef = new HuffmanDecompressor();
    File fileObj = new File("src/textFiles/compressedFiles/nonExistent.txt");

    TreeNode root = new TreeNode('$',5,null,null);


    @Before
    public void setup(){
        root.setLeft(new TreeNode('a',2,null,null));
        root.setRight(new TreeNode('b',3,null,null));
    }

    @Test(expected = RuntimeException.class)
    public void testDecompressFail() {
        testRef.decompress(fileObj);
    }

    @Test(expected = RuntimeException.class)
    public void testDecompressEmpty() {
        fileObj = new File("src/textFiles/compressedFiles/empty.txt");
        testRef.decompress(fileObj);
    }

    @Test
    public void testDecompress(){
        fileObj = new File("src/textFiles/compressedFiles/compressed.txt");
        String actual = testRef.decompress(fileObj);
        String expected = "src/textFiles/decompressedFiles/decompressed.txt";

        assertEquals(expected,actual);
    }

    @Test
    public void dataDecompression() {
        String binaryCode = "10101000";
        int extraBits = 3;

        String actual = testRef.dataDecompression(binaryCode,root,extraBits);
        String expected = "babab";

        assertEquals(expected,actual);
    }

    @Test
    public void testDeserialize() {
        String input = "$,97,#,#,98,#,#";
        TreeNode newRoot = testRef.deserialize(input);

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
    public void treeBuilder() {
    }
}