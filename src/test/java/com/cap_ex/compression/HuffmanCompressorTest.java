package com.cap_ex.compression;

import com.cap_ex.auxiliary.CharComparator;
import com.cap_ex.auxiliary.TreeNode;
import junit.framework.TestResult;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.io.File;
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

    @Test
    public void testGenerateFrequency() {
        fileObj = new File("src/textFiles/testFile.txt");

        Map<Character,Integer> actual = testRef.generateFrequency(fileObj);

        assertEquals(testFreqMap,actual);
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
    public void testCompress() {

    }
}