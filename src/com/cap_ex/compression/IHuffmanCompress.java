package com.cap_ex.compression;

import com.cap_ex.*;

import java.io.File;
import java.util.*;

public interface IHuffmanCompress {
    Map<Character, Integer> generateFrequency(File fileObj);

    List<TreeNode> buildNodeList(Map<Character, Integer> freqTable);

    TreeNode buildTree(List<TreeNode> nodeList);

    Map<Character, String> getCodes(TreeNode root);

    String compress(Map<Character,String> characterCodes,String inputFilePath);


}
