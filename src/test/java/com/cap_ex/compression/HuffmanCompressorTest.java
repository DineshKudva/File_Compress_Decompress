package com.cap_ex.compression;

import com.cap_ex.auxiliary.CharComparator;
import com.cap_ex.auxiliary.TreeNode;
import junit.framework.TestResult;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

public class HuffmanCompressorTest {
    File fileObj;
    IHuffmanCompress testRef = new HuffmanCompressor();
    Map<Character,Integer> testFreqMap;
    Queue<TreeNode> testQueue;

    @Test
    public void testGenerateFrequency() {
        testFreqMap = new HashMap<>();
        fileObj = new File("src/textFiles/testFile.txt");

        testFreqMap.put('a',2);
        testFreqMap.put('b',3);

        Map<Character,Integer> actual = testRef.generateFrequency(fileObj);

        assertEquals(testFreqMap,actual);
    }

    @Test
    public void testBuildNodeQueue() {
            testQueue = new PriorityQueue<>(new CharComparator());
    }

    @Test
    public void testBuildTree() {
    }

    @Test
    public void testGetCodes() {
    }

    @Test
    public void testBuildCode() {
    }

    @Test
    public void testCompress() {
    }
}