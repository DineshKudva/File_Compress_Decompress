package com.cap_ex.compression;

import com.cap_ex.auxiliary.TreeNode;

import java.io.File;
import java.util.*;

public interface IHuffmanCompress {
    Map<Character, Integer> generateFrequency(File fileObj);

    Queue<TreeNode> buildNodeQueue(Map<Character, Integer> freqTable);

    TreeNode buildTree(Queue<TreeNode> nodeQueue);

    Map<Character, String> getCodes(TreeNode root);

    String compress(Map<Character,String> characterCodes, File fileObj, TreeNode root,int size);



}
