package com.cap_ex.compression;


import com.cap_ex.TreeNode;

import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCompressor implements IHuffmanCompress {

    @Override
    public Map<Character, Integer> generateFrequency(String inputFilePath) {
        return null;
    }

    @Override
    public TreeNode buildTree(Map<Character, Integer> freqTable) {
        return null;
    }

    @Override
    public Map<Character, String> getCodes(TreeNode root) {
        return null;
    }

    @Override
    public String compressFile(Map<Character, String> characterCodes, String inputFilePath) {
        return null;
    }
}
