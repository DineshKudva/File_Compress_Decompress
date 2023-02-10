package com.cap_ex.decompression;

import com.cap_ex.auxiliary.TreeNode;
import com.cap_ex.auxiliary.*;

import java.io.*;
import java.util.*;

public class HuffmanDecompressor implements IHuffmanDecompress {

    IGeneralMethods method;
    Scanner fileName = new Scanner(System.in);
    public int nodeListIdx;

    public HuffmanDecompressor(){
        nodeListIdx = 0;
        method = new GeneralMethods();
    }

    public HuffmanDecompressor(IGeneralMethods method){
        nodeListIdx = 0;
        this.method = method;
    }

    @Override
    public IFileContents extractContents(File fileObj) {

        IFileContents fileContents;

        try {

            FileInputStream fin = new FileInputStream(fileObj);
            ObjectInputStream obj = new ObjectInputStream(fin);

            fileContents = new FileContents();

            fileContents.setHuffTree((String)obj.readObject());
            fileContents.setExtraBits((int) obj.readInt());
            fileContents.setByteArray((byte[]) obj.readObject());

            obj.close();
            fin.close();


        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return fileContents;

    }

    @Override
    public String getBinaryData(byte[] byteArray) {
        StringBuilder binaryCode = new StringBuilder();

        int val;

        for(byte myByte:byteArray){
            val = (int) myByte;

            if(val<0)
                val = val+256;

            String binaryEqui = method.getBinaryFromInt(val);

            binaryCode.append(binaryEqui);
        }

        return binaryCode.toString();

    }

    @Override
    public String dataDecompression(String binaryCode, TreeNode root, int extraBits) {

        String resultFilePath = "src/textFiles/decompressedFiles/decompressed.txt";
        File newFile = new File(resultFilePath);

        StringBuilder uncompressedData = new StringBuilder();

        int length = binaryCode.length() - extraBits;
        TreeNode temp = root;

        for(int i=0;i<length;i++) {
            char ch = binaryCode.charAt(i);

            if (ch == '1' && temp.getRightChild() != null)
                temp = temp.getRightChild();
            else if(ch == '0' && temp.getLeftChild() != null)
                temp = temp.getLeftChild();


            if (temp.getLeftChild() == null && temp.getRightChild() == null) {
                uncompressedData.append(temp.getChar());
                temp = root;
            }

        }

        try {
            FileWriter fw = new FileWriter(newFile);
            fw.write(uncompressedData.toString());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Size of decompressed file:"+newFile.length()/1024+" Kb");

        return resultFilePath;
    }

    public TreeNode deserialize(String nodeList){
        if(nodeList == null || nodeList.length() == 0)
            return null;
       nodeListIdx= 0;
       String[] arr = nodeList.split(",");

       TreeNode root = null;
       if(arr.length!=0)
        root = this.treeBuilder(arr);

        return root;
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
