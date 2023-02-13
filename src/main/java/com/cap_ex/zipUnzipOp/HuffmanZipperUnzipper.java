package com.cap_ex.zipUnzipOp;

import com.cap_ex.File_handle.FileHandler;
import com.cap_ex.File_handle.IData_Handle;
import com.cap_ex.auxiliary.GeneralMethods;
import com.cap_ex.auxiliary.TreeNode;
import com.cap_ex.compression.HuffmanCompressor;
import com.cap_ex.compression.IHuffmanCompress;
import com.cap_ex.decompression.HuffmanDecompressor;
import com.cap_ex.decompression.IFileContents;
import com.cap_ex.decompression.IHuffmanDecompress;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class HuffmanZipperUnzipper implements IZipperUnzipper {

    TreeNode root;
    GeneralMethods method = new GeneralMethods();

    @Override
    public String compressFile(String inputFilePath) {

        File fileObj = new File(inputFilePath);

        if (!fileObj.exists())
            return "nan";
        else if (fileObj.length() == 0)
            return "empty";

        IData_Handle dataObj = new FileHandler(inputFilePath);


        IHuffmanCompress compressor = new HuffmanCompressor();

        // frequency table for the characters of the file
        Map<Character, Integer> freqTable = compressor.generateFrequency(dataObj);

        // nodes of the Huffman tree
        Queue<TreeNode> nodeQueue = compressor.buildNodeQueue(freqTable);

        // forming the tree using the nodeList generated
        root = compressor.buildTree(nodeQueue);

        // getting the codes of the characters using the huffman tree
        Map<Character, String> characterCodes = new HashMap<>();

        compressor.getCodes(root, "", characterCodes);

        int byteArraySize = compressor.getArraySize(characterCodes, freqTable);

        String resultPath = compressor.compress(characterCodes, dataObj, root, byteArraySize);

        method.getStats(inputFilePath, resultPath);

        return resultPath;

    }


    @Override
    public String decompressFile(String outputFilePath) {
        File fileObj = new File(outputFilePath);

        if (!fileObj.exists())
            return "nan";
        else if (fileObj.length() == 0)
            return "empty";


        IFileContents fileContents = method.extractContents(fileObj);

        String huffTree = fileContents.getHuffTree();
        int extraBits = fileContents.getExtraBits();
        byte[] byteArray = fileContents.getByteArray();

        IHuffmanDecompress decompressor = new HuffmanDecompressor();

        TreeNode root = decompressor.deserialize(huffTree);

        String binaryData = decompressor.getBinaryData(byteArray);

        String resultPath = decompressor.dataDecompression(binaryData, root, extraBits);

        return resultPath;
    }

}
