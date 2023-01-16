package com.cap_ex.decompression;

import com.cap_ex.TreeNode;

import java.io.*;
import java.util.*;

public class HuffmanDecompressor implements IHuffmanDecompress {

    Scanner fileName = new Scanner(System.in);
    @Override
    public String decompress(File fileObj, TreeNode root,int extraBits) {
        System.out.println("\nEnter name for decompressed file:(without any extensions)");
        String resultFilePath = fileName.nextLine();

        resultFilePath = "/C:/Users/Dinesh/Desktop/"+resultFilePath+".txt";

        try {
            FileWriter fw = new FileWriter(resultFilePath);
            FileReader fileReader = new FileReader(fileObj);

            int val = fileReader.read();
            StringBuilder binaryCode = new StringBuilder();
            char character;

            while(val!=-1){
                character = (char) val;

                String binaryEqui = getBinaryFromChar(character);

                binaryCode.append(binaryEqui);

                val = fileReader.read();
            }

            String decompressedData = dataDecompression(binaryCode.toString(),root,extraBits);

            fw.write(decompressedData);

            fw.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultFilePath;
    }

    @Override
    public String dataDecompression(String binaryCode, TreeNode root,int extraBits) {
        StringBuilder uncompressedData = new StringBuilder();


        int length = binaryCode.length() - extraBits;
        TreeNode temp = root;

        for(int i=0;i<length;i++) {
            char ch = binaryCode.charAt(i);

            if (temp.left == null && temp.right == null) {
                uncompressedData.append(temp.getChar());
                temp = root;
                continue;
            }

            if (ch == '1')
                temp = temp.right;
            else
                temp = temp.left;


            if (temp.left == null && temp.right == null) {
                uncompressedData.append(temp.getChar());
                temp = root;
            }

        }

        return uncompressedData.toString();
    }

    @Override
    public String getBinaryFromChar(char ch) {
        int deciVal = ch;
        String result = "";

        while (deciVal != 0) {
            int rem = deciVal % 2;
            deciVal /= 2;
            result = rem + result;
        }

        while (result.length() != 7)
            result = '0' + result;

        return result;
    }
}
