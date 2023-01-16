package com.cap_ex;

import com.cap_ex.compression.HuffmanCompressor;
import com.cap_ex.compression.IHuffmanCompress;
import com.cap_ex.decompression.HuffmanDecompressor;
import com.cap_ex.decompression.IHuffmanDecompress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanZipperUnzipper implements IZipperUnzipper {

    TreeNode root;
    int size_compressed_data,rem_bits;

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

//        inorderTraversal(root);

        // getting the codes of the characters using the huffman tree
        Map<Character,String> characterCodes = compressor.getCodes(root);

        String outputFilePath = compressor.compress(characterCodes, fileObj);

        for (Map.Entry<Character, String> mapElement : characterCodes.entrySet()) {
            char ch = mapElement.getKey();
            String str = mapElement.getValue();
            System.out.println("Character : {} \t" + ch + "\t code:" + str);
        }

        size_compressed_data = calculateSize(freqTable,characterCodes);
        rem_bits = HuffmanCompressor.extraBits;

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

        String resultPath = decompressor.decompress(filObj,root,rem_bits);

        return resultPath;
    }
}
