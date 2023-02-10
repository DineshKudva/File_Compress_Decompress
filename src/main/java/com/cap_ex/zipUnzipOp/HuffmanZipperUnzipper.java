package com.cap_ex.zipUnzipOp;

import com.cap_ex.File_handle.FileHandler;
import com.cap_ex.File_handle.IData_Handle;
import com.cap_ex.auxiliary.GeneralMethods;
import com.cap_ex.auxiliary.TreeNode;
import com.cap_ex.compression.*;
import com.cap_ex.decompression.*;

import java.io.*;
import java.util.*;

public class HuffmanZipperUnzipper implements IZipperUnzipper {

    TreeNode root;
    GeneralMethods method = new GeneralMethods();

    @Override
    public String compressFile(String inputFilePath) {

        File fileObj = new File(inputFilePath);

        if(!fileObj.exists())
            return "nan";
        else if(fileObj.length() == 0)
            return "empty";

        IHuffmanCompress compressor = new HuffmanCompressor();

        IData_Handle dataObj = new FileHandler(inputFilePath);

        // frequency table for the characters of the file
//        Map<Character,Integer> freqTable = compressor.generateFrequency(fileObj);

        Map<Character,Integer> freqTable = compressor.generateFrequency(dataObj);


        // nodes of the Huffman tree
        Queue<TreeNode> nodeQueue = compressor.buildNodeQueue(freqTable);

        // forming the tree using the nodeList generated
        root = compressor.buildTree(nodeQueue);

        // getting the codes of the characters using the huffman tree
        Map<Character,String> characterCodes = new HashMap<>();

        compressor.getCodes(root,"",characterCodes);

        int byteArraySize = compressor.getArraySize(characterCodes,freqTable);


//        return compressor.compress(characterCodes, fileObj,root,byteArraySize);

        return compressor.compress(characterCodes, dataObj,root,byteArraySize);


    }
    

    @Override
    public String decompressFile(String outputFilePath) {
        File fileObj = new File(outputFilePath);

        if(!fileObj.exists())
            return "nan";
        else if(fileObj.length() == 0)
            return "empty";

        IHuffmanDecompress decompressor = new HuffmanDecompressor();


        IFileContents fileContents = decompressor.extractContents(fileObj);

        String huffTree = fileContents.getHuffTree();
        int extraBits = fileContents.getExtraBits();
        byte[] byteArray = fileContents.getByteArray();

//        System.out.println(huffTree+"\t"+extraBits);


        TreeNode root = decompressor.deserialize(huffTree);

        String binaryData = decompressor.getBinaryData(byteArray);

        String resultPath = decompressor.dataDecompression(binaryData,root,extraBits);

        return resultPath;
    }

}
