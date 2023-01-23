package com.cap_ex;

import com.cap_ex.compression.*;
import com.cap_ex.decompression.*;

import java.io.*;
import java.util.*;

public class HuffmanZipperUnzipper implements IZipperUnzipper {

    TreeNode root;
    private int sizeCompressedData,remBits;

    @Override
    public String compressFile(String inputFilePath) {

        File fileObj = new File(inputFilePath);

        IHuffmanCompress compressor = new HuffmanCompressor();

        // frequency table for the characters of the file
        Map<Character,Integer> freqTable = compressor.generateFrequency(fileObj);

        // nodes of the Huffman tree
        List<TreeNode> nodeList = compressor.buildNodeList(freqTable);

        // forming the tree using the nodeList generated
        root = compressor.buildTree(nodeList);

        inorderTraversal(root);

        // getting the codes of the characters using the huffman tree
        Map<Character,String> characterCodes = compressor.getCodes(root);

        String outputFilePath = compressor.compress(characterCodes, fileObj,root);


        sizeCompressedData = calculateSize(freqTable,characterCodes);
        remBits = HuffmanCompressor.extraBits;

        return outputFilePath;
    }

    private int calculateSize(Map<Character, Integer> freqTable, Map<Character, String> characterCodes) {
        int size = 0;

        for(Map.Entry<Character,Integer> mapEle : freqTable.entrySet()){
            char ch = mapEle.getKey();
            int freq = mapEle.getValue();
            int bitSize = characterCodes.get(ch).length();

            size += (freq*bitSize);

        }
        return size;
    }

    private void inorderTraversal(TreeNode root) {
        if (root == null)
            return;

        inorderTraversal(root.left);
        System.out.print(root.getChar() + "\t");
        inorderTraversal(root.right);
    }

    @Override
    public String decompressFile(String outputFilePath) {
        File filObj = new File(outputFilePath);

        IHuffmanDecompress decompressor = new HuffmanDecompressor();

        String resultPath = decompressor.decompress(filObj,root,remBits);

        System.out.println("Compressed and Decompressed files are generated successfully.\nCompressed file can be found at:"+outputFilePath+"\nDecompressed file can be found at:"+resultPath);

        return resultPath;
    }
}
