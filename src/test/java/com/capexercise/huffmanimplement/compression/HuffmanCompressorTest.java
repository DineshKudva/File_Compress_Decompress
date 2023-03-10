package com.capexercise.huffmanimplement.compression;

import com.capexercise.filehandle.*;
import com.capexercise.huffmanimplement.auxiliary.*;
import com.capexercise.huffmanimplement.compression.*;
import org.junit.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HuffmanCompressorTest {
    File fileObj;

    IData_Handle dataObj;
    IGeneralMethods method;
    IHuffmanCompress testRef;
    Map<Character,Integer> testFreqMap = new HashMap<>();
    Queue<TreeNode> testQueue = new PriorityQueue<>(new CharComparator()) ;
    TreeNode root = new TreeNode('$','5',null,null);

    Map<Character,String> testCodes = new HashMap<>();

    @Before
    public void setup(){



        method = mock(IGeneralMethods.class);
        testRef = new HuffmanCompressor();

        testFreqMap.put('a',6);
        testFreqMap.put('b',7);

        testQueue.add(new TreeNode('a',6,null,null));
        testQueue.add(new TreeNode('b',7,null,null));

        root.setLeft(new TreeNode('a',6,null,null));
        root.setRight(new TreeNode('b',7,null,null));

        testCodes.put('a',"0");
        testCodes.put('b',"1");
    }

    @Test(expected = RuntimeException.class)
    public void testGenerateFrequencyForNull(){
        dataObj = new FileHandler("src/textFiles/nonexistent.txt");
        Map<Character, Integer> actual = testRef.generateFrequency(dataObj);
    }

    @Test
    public void testGenerateFrequencyForEmpty(){
        dataObj = new FileHandler("src/textFiles/empty.txt");
        Map<Character, Integer> actual = testRef.generateFrequency(dataObj);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void testGenerateFrequencyForNormalFile(){
        dataObj = new FileHandler("src/textFiles/testFile.txt");
        Map<Character,Integer> actual = testRef.generateFrequency(dataObj);
        assertEquals(testFreqMap,actual);
    }

    @Test
    public void testGenerateFrequencyForString() {
        dataObj = new StringHandler("ababaabaabbbb");
        Map<Character,Integer> actual = testRef.generateFrequency(dataObj);
        assertEquals(testFreqMap,actual);
    }

    @Test
    public void testGenerateFrequencyForByteArray(){
        byte[] byteArray = "ababaabaabbbb".getBytes();
        dataObj = new StringHandler(byteArray);
        Map<Character,Integer> actual = testRef.generateFrequency(dataObj);
        assertEquals(testFreqMap,actual);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildNodeQueueForNull(){
        Queue<TreeNode> actual = testRef.buildNodeQueue(null);
    }

    @Test
    public void testBuildNodeQueueForEmpty(){
        Map<Character,Integer> emptyMap = new HashMap<>();
        Queue<TreeNode> actual = testRef.buildNodeQueue(emptyMap);
        assertTrue(actual.size() == 0);
    }

    @Test
    public void testBuildNodeQueue() {
            boolean flag = true;
            Queue<TreeNode> actual = testRef.buildNodeQueue(testFreqMap);

            assertEquals(testQueue.size(),actual.size());

            StringBuilder exp = new StringBuilder();
            for(TreeNode node : testQueue){
                exp.append(node.getChar());
            }
            for(TreeNode node:actual){
                if(node.getChar() != exp.charAt(0))
                {
                    flag = false;
                    break;
                }
                exp.deleteCharAt(0);
            }
            assertTrue(flag);


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

    @Test(expected = NullPointerException.class)
        public void testGetArraySizeForNulls(){
            int actual = testRef.getArraySize(null,testFreqMap);

        }


    @Test
    public void testGetArraySizeZero(){
        Map<Character,Integer> emptyFreqMap = new HashMap<>();
        Map<Character,String> emptyCodeMap = new HashMap<>();
        int actaul = testRef.getArraySize(emptyCodeMap,emptyFreqMap);
        assertEquals(0,actaul);
    }

    @Test
    public void testGetArraySize(){
        int actaul = testRef.getArraySize(testCodes,testFreqMap);
        int expected = 2;
        assertEquals(expected, actaul);
    }

    @Test(expected = RuntimeException.class)
    public void testCompressForNull(){
        dataObj = new FileHandler("src/textFiles/nonExistent.txt");
        IFileContents fileContents = testRef.compress(testCodes,dataObj,root,20);
    }

    @Test
    public void testCompressForFile() {

        testRef = new HuffmanCompressor(method);

        dataObj = new FileHandler("src/textFiles/testFile.txt");

        int size = 2;
        when(method.serialize(root)).thenReturn("$,97,#,#,98,#,#");

        IFileContents fileContents = testRef.compress(testCodes,dataObj,root,size);

        byte[] expectedByteArray = {82,120};
        byte[] actualByteArray = fileContents.getByteArray();


        boolean identityFlag = true;

        assertEquals("length of expected and actual byte array not equal",expectedByteArray.length,actualByteArray.length);

        for(int i=0;i<expectedByteArray.length;i++){
            if(actualByteArray[i]!=expectedByteArray[i]){
                identityFlag = false;
                break;
            }
        }

        assertTrue("byte array formed is incorrect",identityFlag);

    }

    @Test
    public void testCompressForString() {

        testRef = new HuffmanCompressor(method);

        dataObj = new StringHandler("ababaabaabbbb");

        int size = 2;
        when(method.serialize(root)).thenReturn("$,97,#,#,98,#,#");

        IFileContents fileContents = testRef.compress(testCodes,dataObj,root,size);

        byte[] expectedByteArray = {82,120};
        byte[] actualByteArray = fileContents.getByteArray();

        boolean identityFlag = true;

        assertEquals("length of expected and actual byte array not equal",expectedByteArray.length,actualByteArray.length);

        for(int i=0;i<expectedByteArray.length;i++){
            if(actualByteArray[i]!=expectedByteArray[i]){
                identityFlag = false;
                break;
            }
        }

        assertTrue("byte array formed is incorrect",identityFlag);
    }


}