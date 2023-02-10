package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;

import java.io.File;

public interface IHuffmanDecompress {
        IFileContents extractContents(File fileObj);

        String getBinaryData(byte[] fileContents);

        String dataDecompression(String compressedData, TreeNode root, int size);

        public TreeNode deserialize(String nodeList);

        public TreeNode treeBuilder(String[] arr);

}
