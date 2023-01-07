package com.cap_ex.compression;

import com.cap_ex.TreeNode;

import java.util.Map;
import java.util.PriorityQueue;

public class Compressor implements ICompress{
    @Override
    public Map<Character, Integer> generateFrequency(String file_path) {
        return null;
    }

    @Override
    public PriorityQueue<TreeNode> buildTree(Map<Character, Integer> freqTable) {
        return null;
    }

    @Override
    public String writeFile(TreeNode root, String file_path) {
        return null;
    }

    String compressFile(String file_path){
        String final_file_path = "";

        ICompress compressObj = new Compressor();

        return final_file_path;
    }
}
