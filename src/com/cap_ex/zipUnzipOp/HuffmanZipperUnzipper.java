package com.cap_ex.zipUnzipOp;

import com.cap_ex.auxiliary.TreeNode;
import com.cap_ex.compression.*;
import com.cap_ex.decompression.*;

import java.io.*;
import java.util.*;

public class HuffmanZipperUnzipper implements IZipperUnzipper {

    TreeNode root;

    @Override
    public String compressFile(String inputFilePath) {

        File fileObj = new File(inputFilePath);

        if(!fileObj.exists())
            return "";

        IHuffmanCompress compressor = new HuffmanCompressor();

        // frequency table for the characters of the file
        Map<Character,Integer> freqTable = compressor.generateFrequency(fileObj);

        // nodes of the Huffman tree
        Queue<TreeNode> nodeQueue = compressor.buildNodeQueue(freqTable);

        // forming the tree using the nodeList generated
        root = compressor.buildTree(nodeQueue);

        // getting the codes of the characters using the huffman tree
        Map<Character,String> characterCodes = compressor.getCodes(root);


        return compressor.compress(characterCodes, fileObj,root);

    }
    

    @Override
    public String decompressFile(String outputFilePath) {
        File fileObj = new File(outputFilePath);

        if(!fileObj.exists())
            return "";

        IHuffmanDecompress decompressor = new HuffmanDecompressor();

        String resultPath = decompressor.decompress(fileObj);

        return resultPath;
    }
}
