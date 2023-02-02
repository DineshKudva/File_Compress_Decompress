package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;
import com.cap_ex.auxiliary.*;

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
            treeRoot = deserialize(huffTree);

            extraBits = obj.readInt();

            byte[] byteArray = (byte[]) obj.readObject();

            StringBuilder binaryCode = new StringBuilder();

            int val;

            for(byte myByte:byteArray){
                val = (int) myByte;

                if(val<0)
                    val = (val+256)%256;

                String binaryEqui = method.getBinaryFromInt(val);

                binaryCode.append(binaryEqui);
        }

            obj.close();
            fin.close();

            String decompressedData = dataDecompression(binaryCode.toString(),treeRoot,extraBits);

            FileWriter fw = new FileWriter(resultFilePath);

            fw.write(decompressedData);

            fw.close();


        } catch (IOException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Size of decompressed file:"+newFile.length()/1024+" Kb");
        return resultFilePath;
    }

    @Override
    public String dataDecompression(String binaryCode, TreeNode root, int extraBits) {
        StringBuilder uncompressedData = new StringBuilder();


        int length = binaryCode.length() - extraBits;
        TreeNode temp = root;

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
