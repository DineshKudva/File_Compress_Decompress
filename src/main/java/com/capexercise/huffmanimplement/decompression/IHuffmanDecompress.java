package com.capexercise.huffmanimplement.decompression;

import com.capexercise.huffmanimplement.auxiliary.TreeNode;

public interface IHuffmanDecompress {


    String getBinaryData(byte[] fileContents);

    String dataDecompression(String compressedData, TreeNode root, int size);

    TreeNode deserialize(String nodeList);

    TreeNode treeBuilder(String[] arr);

}
