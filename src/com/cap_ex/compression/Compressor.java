package com.cap_ex.compression;


import com.cap_ex.TreeNode;

import java.util.Map;
import java.util.PriorityQueue;

public class Compressor implements ICompress{
    @Override
    public Map<Character, Integer> generateFrequency(String filePath) {
        return null;
    }

    @Override
    public PriorityQueue<TreeNode> buildTree(Map<Character, Integer> freqTable) {
        return null;
    }

    @Override
    public String writeFile(TreeNode root, String filePath) {
        return null;
    }

    String compressFile(String filePath){
        String finalFilePath = "";

        return finalFilePath;
    }
}
