package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;
import com.cap_ex.auxiliary.*;
import sun.reflect.generics.tree.Tree;

import javax.xml.soap.Node;
import java.io.*;
import java.util.*;

public class HuffmanDecompressor implements IHuffmanDecompress {

    IGeneralMethods method = new GeneralMethods();
    Scanner fileName = new Scanner(System.in);
    int nodeListIdx;
    @Override
    public String decompress(File fileObj) {
        System.out.println("\nEnter name for decompressed file:(without any extensions)");
        String resultFilePath = fileName.nextLine();


        resultFilePath = "/home/dineshkudwa/Desktop/text_files/"+resultFilePath+".txt";
        File newFile = new File(resultFilePath);


        try {

            TreeNode treeRoot = null;
            int extraBits;

            FileInputStream fin = new FileInputStream(fileObj);
            ObjectInputStream obj = new ObjectInputStream(fin);

            String huffTree = (String) obj.readObject();
            extraBits = obj.readInt();
            byte[] byteArray = (byte[]) obj.readObject();

            obj.close();
            fin.close();

            treeRoot = deserialize(huffTree);
//            StringBuilder binaryCode = new StringBuilder();
//            int val;
//
//            for(byte myByte:byteArray){
//                val = (int) myByte;
//
//                if(val<0)
//                    val = (val+256)%256;
//
//                String binaryEqui = method.getBinaryFromInt(val);
//
//                binaryCode.append(binaryEqui);
//        }
//
//            String decompressedData = dataDecompression(binaryCode.toString(),treeRoot,extraBits);
//
//            FileWriter fw = new FileWriter(resultFilePath);
//
//            fw.write(decompressedData);
//
//            fw.close();
              dataDecompress(treeRoot,extraBits,byteArray,resultFilePath);

        } catch (IOException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Size of decompressed file:"+newFile.length()/1024+" Kb");
        return resultFilePath;
    }

    public void dataDecompress(TreeNode root,int extraBits,byte[] byteArray,String filePath){
        try {
            // set temp to root
            TreeNode temp  = root;

            //obtain last index
            int n =  byteArray.length;

            String curCode = "";

            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);

            for(int i=0;i<n;i++){

                // obtain a byte
                int val = (int) byteArray[i];

                if(val<0)
                    val = (val+256)%256;

                // store binary in string
                curCode = method.getBinaryFromInt(val);

                // check for last byte
                if(i==n-1){
                    curCode = curCode.substring(0,8-extraBits);
                }

                for(char ch:curCode.toCharArray()){
                    // for tree with a single node
                    if (temp.getLeftChild() == null && temp.getRightChild() == null) {
                        pw.print(temp.getChar());
                        temp = root;
                        continue;
                    }

                    if (ch == '0') // left move
                        temp = temp.getLeftChild();
                    else // right move
                        temp = temp.getRightChild();

                    // reached leaf
                    if (temp.getLeftChild() == null && temp.getRightChild() == null) {
                        pw.print(temp.getChar());
                        temp = root;
                    }
                }

            }

            pw.close();
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String dataDecompression(String binaryCode, TreeNode root, int extraBits) {
        StringBuilder uncompressedData = new StringBuilder();

        System.out.println(binaryCode.toString());
        System.out.println(binaryCode.length());
        System.out.println(extraBits);
        int length = binaryCode.length() - extraBits;
        TreeNode temp = root;

        System.out.println(length);

        for(int i=0;i<length;i++) {
            char ch = binaryCode.charAt(i);

            if (temp.getLeftChild() == null && temp.getRightChild() == null) {
                uncompressedData.append(temp.getChar());
                temp = root;
                continue;
            }

            if (ch == '1')
                temp = temp.getRightChild();
            else
                temp = temp.getLeftChild();


            if (temp.getLeftChild() == null && temp.getRightChild() == null) {
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

        return treeBuilder(arr);
    }

    public TreeNode treeBuilder(String[] arr){
        if(arr[nodeListIdx].equals("#"))
            return null;

        TreeNode root;

        if(arr[nodeListIdx].equals("$"))
            root = new TreeNode(arr[nodeListIdx].charAt(0),0,null,null);
        else
            root = new TreeNode((char)Integer.parseInt(arr[nodeListIdx]),0,null,null);

       nodeListIdx++;
        root.setLeft(treeBuilder(arr));
       nodeListIdx++;
          root.setRight(treeBuilder(arr));
        return root;
    }


}

