package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Stack;

import static org.junit.Assert.*;

public class HuffmanDecompressorTest {
    IHuffmanDecompress testRef = new HuffmanDecompressor();
    File fileObj = new File("src/textFiles/compressedFiles/nonExistent.txt");

    TreeNode root = new TreeNode('$',13,null,null);


    @Before
    public void setup(){
        root.setLeft(new TreeNode('a',6,null,null));
        root.setRight(new TreeNode('b',7,null,null));
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
        fileObj = new File("src/textFiles/compressedFiles/testCompressed.txt");
        String actual = testRef.decompress(fileObj);
        String expected = "src/textFiles/decompressedFiles/testDecompressed.txt";

        boolean identityFlag = true;

        FileReader f1,f2;
        BufferedReader br1,br2;

        try {
            f1 = new FileReader(new File(actual));
            br1 = new BufferedReader(f1);

            f2 = new FileReader(new File(expected));
            br2 = new BufferedReader(f2);

//            String line1 = br1.readLine();

            while(br1.ready() && br2.ready()){
                if(!br1.readLine().equals(br2.readLine())){
                    identityFlag = false;
                    break;
                }

            }

            if(br1.ready() || br2.ready())
                identityFlag = false;

            br2.close();
            br1.close();

            f2.close();
            f1.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        assertTrue(identityFlag);
    }

    @Test
    public void dataDecompression() {
        String binaryCode = "10101000";
        int extraBits = 1;

        String actual = testRef.dataDecompression(binaryCode,root,extraBits);
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