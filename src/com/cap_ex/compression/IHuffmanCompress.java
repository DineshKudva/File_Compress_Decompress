package com.cap_ex.compression;

import com.cap_ex.*;

import java.util.*;

public interface IHuffmanCompress {
    Map<Character, Integer> generateFrequency(String inputFilePath);

    TreeNode buildTree(Map<Character, Integer> freqTable);

    Map<Character, String> getCodes(TreeNode root);

    String compressFile(Map<Character, String> characterCodes, String inputFilePath);


}
