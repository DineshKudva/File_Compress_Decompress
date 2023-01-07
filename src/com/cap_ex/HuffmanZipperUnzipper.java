package com.cap_ex;

import com.cap_ex.compression.HuffmanCompressor;
import com.cap_ex.compression.IHuffmanCompress;

import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanZipperUnzipper implements IZipperUnzipper {

    @Override
    public String compressFile(String inputFilePath) {
        IHuffmanCompress compressor = new HuffmanCompressor();

        Map<Character,Integer> freqTable = compressor.generateFrequency(inputFilePath);

        TreeNode root = compressor.buildTree(freqTable);

        Map<Character,String> characterCodes = compressor.getCodes(root);

        String outputFilePath = compressor.compressFile(characterCodes, inputFilePath);

        return outputFilePath;
    }

    @Override
    public String decompressFile(String outputFilePath) {
        return null;
    }
}
