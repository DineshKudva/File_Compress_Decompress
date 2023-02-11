package com.cap_ex.compression;

import com.cap_ex.File_handle.IData_Handle;
import com.cap_ex.auxiliary.TreeNode;

import java.util.Map;
import java.util.Queue;

public interface IHuffmanCompress {
    Map<Character, Integer> generateFrequency(IData_Handle dataObj);

    Queue<TreeNode> buildNodeQueue(Map<Character, Integer> freqTable);

    TreeNode buildTree(Queue<TreeNode> nodeQueue);

    void getCodes(TreeNode root, String code, Map<Character, String> characterCodes);

    int getArraySize(Map<Character, String> charMap, Map<Character, Integer> freqMap);

    String compress(Map<Character, String> characterCodes, IData_Handle dataObj, TreeNode root, int size);


}
