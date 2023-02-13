package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;

public interface IHuffmanDecompress {


    String getBinaryData(byte[] fileContents);

    String dataDecompression(String compressedData, TreeNode root, int size);

    TreeNode deserialize(String nodeList);

    TreeNode treeBuilder(String[] arr);

}
