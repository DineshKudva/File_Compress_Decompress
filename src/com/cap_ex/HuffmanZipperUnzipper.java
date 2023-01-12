package com.cap_ex;

import com.cap_ex.compression.HuffmanCompressor;
import com.cap_ex.compression.IHuffmanCompress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanZipperUnzipper implements IZipperUnzipper {

    @Override
    public String compressFile(String inputFilePath) {

        File fileObj = new File(inputFilePath);

        IHuffmanCompress compressor = new HuffmanCompressor();

        // frequency table for the characters of the file
        Map<Character,Integer> freqTable = compressor.generateFrequency(fileObj);

        // nodes of the Huffman tree
        List<TreeNode> nodeList = compressor.buildNodeList(freqTable);

        // forming the tree using the nodeList generated
        TreeNode root = compressor.buildTree(nodeList);

        inorderTraversal(root);

        // getting the codes of the characters using the huffman tree
        Map<Character,String> characterCodes = compressor.getCodes(root);

        String outputFilePath = compressor.compress(characterCodes, inputFilePath);

        return outputFilePath;
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
        return null;
    }
}
