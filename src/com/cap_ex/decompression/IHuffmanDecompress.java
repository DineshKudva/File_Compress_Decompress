package com.cap_ex.decompression;

import com.cap_ex.TreeNode;

import java.io.File;

public interface IHuffmanDecompress {
        String decompress(File fileObj,TreeNode root,int size);

        String dataDecompression(String compressedData,TreeNode root,int size);

        String getBinaryFromChar(char ch);
}
