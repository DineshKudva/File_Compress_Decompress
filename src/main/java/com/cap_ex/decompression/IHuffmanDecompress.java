package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;

import java.io.File;

public interface IHuffmanDecompress {
        String decompress(File fileObj);

        String dataDecompression(String compressedData, TreeNode root, int size);

        public TreeNode deserialize(String nodeList);


}
