package com.cap_ex.decompression;

import com.cap_ex.TreeNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HuffmanDecompressor implements IHuffmanDecompress {

    Scanner fileName = new Scanner(System.in);
    @Override
    public String decompress(File fileObj, TreeNode root,int size) {
        System.out.println("\nEnter name for decompressed file:(without any extensions)");
        String resultFilePath = fileName.nextLine();

        resultFilePath = "/C:/Users/Dinesh/Desktop/"+resultFilePath+".txt";

        try {
            FileWriter fw = new FileWriter(resultFilePath);
            Scanner fileScanner = new Scanner(fileObj);

            while(fileScanner.hasNext()){
                String data = fileScanner.nextLine();

                String decompressedData = dataDecompression(data,root,size);
                fw.write(decompressedData);

            }

            System.out.println("Out of while!");

            fileScanner.close();
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultFilePath;
    }

    @Override
    public String dataDecompression(String compressedData, TreeNode root,int size_of_compressed_string) {
        String binaryStr = "";
        StringBuilder uncompressedData = new StringBuilder();

        System.out.println(compressedData);

        for (char ch : compressedData.toCharArray()) {
            String binary_val = getBinaryFromChar(ch);
            binaryStr += binary_val;
        }


//        binaryStr = binaryStr.substring(0, size_of_compressed_string);
        TreeNode temp = root;

        for (char ch : binaryStr.toCharArray()) {

//            if (temp.left == null && temp.right == null) {
//                System.out.println(temp.getChar());
//                uncompressedData.append(temp.getChar());
//                temp = root;
//                continue;
//            }

            if (ch == '1')
                temp = temp.right;
            else
                temp = temp.left;


            if (temp.left == null && temp.right == null) {
//                System.out.println(temp.getChar()+"\t"+temp.getFreq());
                uncompressedData.append(temp.getChar());
                temp = root;
            }
        }
        System.out.println(uncompressedData.toString());
        return uncompressedData.toString();
    }

    @Override
    public String getBinaryFromChar(char ch) {
        int deci_val = ch;
        String result = "";

        while (deci_val != 0) {
            int rem = deci_val % 2;
            deci_val /= 2;
            result = rem + result;
        }

        while (result.length() != 7)
            result = '0' + result;

        return result;
    }
}
