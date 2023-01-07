package com.cap_ex.compression;

import com.cap_ex.*;

import java.util.*;

public interface ICompress {

    Map<Character,Integer> generateFrequency(String file_path);
    PriorityQueue<TreeNode> buildTree(Map<Character,Integer> freqTable);

    String writeFile(TreeNode root, String file_path);

}
