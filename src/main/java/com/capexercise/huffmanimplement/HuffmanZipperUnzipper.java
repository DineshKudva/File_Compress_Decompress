package com.capexercise.huffmanimplement;

import com.capexercise.filehandle.FileHandler;
import com.capexercise.filehandle.IData_Handle;
import com.capexercise.huffmanimplement.auxiliary.GeneralMethods;
import com.capexercise.huffmanimplement.auxiliary.TreeNode;
import com.capexercise.huffmanimplement.compression.HuffmanCompressor;
import com.capexercise.huffmanimplement.compression.IHuffmanCompress;
import com.capexercise.huffmanimplement.decompression.HuffmanDecompressor;
import com.capexercise.huffmanimplement.auxiliary.IFileContents;
import com.capexercise.huffmanimplement.decompression.IHuffmanDecompress;
import com.capexercise.zipUnzipOp.IZipperUnzipper;

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

        IFileContents compressedData = compressor.compress(characterCodes, dataObj, root, byteArraySize);

        String resultPath = method.addCompressedContent(compressedData);

//        long fileSize = new File(inputFilePath).length();
//        float codeSize = (float) byteArraySize*8;
//        System.out.println(codeSize/fileSize);

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
