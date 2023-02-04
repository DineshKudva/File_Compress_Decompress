package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;

import java.io.File;

public interface IHuffmanDecompress {
        String decompress(File fileObj);

        void dataDecompress(TreeNode root,int extraBits,byte[] byteArray,String filePath);

}
