package com.cap_ex.compression;

import com.cap_ex.auxiliary.CharComparator;
import com.cap_ex.auxiliary.TreeNode;
import junit.framework.TestResult;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class HuffmanCompressorTest {
    File fileObj;
    IHuffmanCompress testRef = new HuffmanCompressor();
    Map<Character,Integer> testFreqMap = new HashMap<>();
    Queue<TreeNode> testQueue = new PriorityQueue<>(new CharComparator()) ;
    TreeNode root = new TreeNode('$','5',null,null);

    Map<Character,String> testCodes = new HashMap<>();

    @Before
    public void setup(){
        testFreqMap.put('a',2);
        testFreqMap.put('b',3);

        testQueue.add(new TreeNode('a',2,null,null));
        testQueue.add(new TreeNode('b',3,null,null));

        root.setLeft(new TreeNode('a',2,null,null));
        root.setRight(new TreeNode('b',3,null,null));

        testCodes.put('a',"0");
        testCodes.put('b',"1");
    }

    @Test(expected = RuntimeException.class)
    public void testGenerateFrequencyForNull(){
        fileObj = new File("src/textFiles/nonexistent.txt");
        Map<Character, Integer> actual = testRef.generateFrequency(fileObj);
    }
    @Test
    public void testGenerateFrequency() {
        fileObj = new File("src/textFiles/testFile.txt");

        Map<Character,Integer> actual = testRef.generateFrequency(fileObj);

        assertEquals(testFreqMap,actual);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildNodeQueueForNull(){
        Queue<TreeNode> actual = testRef.buildNodeQueue(null);
    }

    @Test
    public void testBuildNodeQueue() {

            Queue<TreeNode> actual = testRef.buildNodeQueue(testFreqMap);
            assertEquals(testQueue.size(),actual.size());
    }

    @Test
    public void testBuildTree() {
        TreeNode newRoot = testRef.buildTree(testQueue);
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
    public void testGetCodes() {
        Map<Character,String> generatedCodes = new HashMap<>();
        testRef.getCodes(root,"",generatedCodes);
        boolean flag = true;
        for(Map.Entry<Character,String> mapEle : testCodes.entrySet()){
            if(!mapEle.getValue().equals(generatedCodes.get(mapEle.getKey())))
            {
                flag = false;
                break;
            }
        }

        assertTrue("The map generated is incorrect",flag);
    }

    @Test
    public void testGetArraySizeZero(){
        testCodes.clear();
        testFreqMap.clear();
        int actaul = testRef.getArraySize(testCodes,testFreqMap);
        assertEquals(0,actaul);
    }

    @Test
    public void testGetArraySize(){
        testFreqMap.put('a',2);
        testFreqMap.put('b',3);

        testCodes.put('a',"0");
        testCodes.put('b',"1");

        int actaul = testRef.getArraySize(testCodes,testFreqMap);
        int expected = 1;
        assertEquals(expected, actaul);
    }

    @Test(expected = RuntimeException.class)
    public void testCompressForNull(){
        fileObj = new File("src/textFiles/nonExistent.txt");
        String actual = testRef.compress(testCodes,fileObj,root,20);
    }

    @Test
    public void testCompress() {
        fileObj = new File("src/textFiles/largeTest2.txt");

        testFreqMap = testRef.generateFrequency(fileObj);
        testQueue = testRef.buildNodeQueue(testFreqMap);
        root = testRef.buildTree(testQueue);
        testCodes.clear();
        testRef.getCodes(root,"",testCodes);
        int size = testRef.getArraySize(testCodes,testFreqMap);

        String actual = testRef.compress(testCodes,fileObj,root,size);
        String expected = "src/textFiles/compressedFiles/testCompressed.txt";

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


}