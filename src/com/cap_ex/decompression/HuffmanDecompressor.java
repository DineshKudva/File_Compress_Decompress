package com.cap_ex.decompression;

import com.cap_ex.TreeNode;
import com.cap_ex.auxiliary.GeneralMethods;
import com.cap_ex.auxiliary.IGeneralMethods;

import java.io.*;
import java.util.*;

public class HuffmanDecompressor implements IHuffmanDecompress {

    IGeneralMethods method = new GeneralMethods();
    Scanner fileName = new Scanner(System.in);
    int nodeListIdx;
    @Override
    public String decompress(File fileObj, TreeNode root, int extraBits) {
        System.out.println("\nEnter name for decompressed file:(without any extensions)");
        String resultFilePath = fileName.nextLine();


        resultFilePath = "/C:/Users/Dinesh/Desktop/"+resultFilePath+".txt";

        try {

            TreeNode treeRoot = null;
            int extra_bits;

            FileWriter fw = new FileWriter(resultFilePath);
            FileReader fileReader = new FileReader(fileObj);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String huffTree = bufferedReader.readLine();

            treeRoot = deserialize(huffTree);

            String extra = bufferedReader.readLine();
            extra_bits = Integer.parseInt(extra);
            System.out.println("Extra bits:"+extra_bits);

            int val = bufferedReader.read();
            StringBuilder binaryCode = new StringBuilder();
            char character;

            while(val!=-1){
                character = (char) val;

                String binaryEqui = method.getBinaryFromChar(character);

                binaryCode.append(binaryEqui);

                val = bufferedReader.read();
            }

            bufferedReader.close();
            fileReader.close();

            String decompressedData = dataDecompression(binaryCode.toString(),treeRoot,extra_bits);

            fw.write(decompressedData);

            fw.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultFilePath;
    }

    @Override
    public String dataDecompression(String binaryCode, TreeNode root, int extra_bits) {
        StringBuilder uncompressedData = new StringBuilder();

        binaryCode = binaryCode.substring(0,binaryCode.length()-14);

        int length = binaryCode.length() - extra_bits;
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

    public TreeNode deserialize(String nodeList){
        if(nodeList == null)
            return null;
       nodeListIdx= 0;
        String[] arr = nodeList.split(",");
//        for(String x:arr){
//            System.out.print(x+"\t");
//        }
//        System.out.println();
        return treeBuilder(arr);
    }

    public TreeNode treeBuilder(String[] arr){
        if(arr[nodeListIdx].equals("#"))
            return null;

        TreeNode root;

        if(arr[nodeListIdx].equals("$"))
            root = new TreeNode(arr[nodeListIdx].charAt(0),0);
        else
            root = new TreeNode((char)Integer.parseInt(arr[nodeListIdx]),0);

       nodeListIdx++;
        root.left = treeBuilder(arr);
       nodeListIdx++;
        root.right = treeBuilder(arr);

        return root;
    }
//
//    @Override
//    public String getBinaryFromChar(char ch) {
//        int deciVal = ch;
//        String result = "";
//
//        while (deciVal != 0) {
//            int rem = deciVal % 2;
//            deciVal /= 2;
//            result = rem + result;
//        }
//
//        while (result.length() != 7)
//            result = '0' + result;
//
//        return result;
//    }

}
