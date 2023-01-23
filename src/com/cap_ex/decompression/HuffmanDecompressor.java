package com.cap_ex.decompression;

import com.cap_ex.TreeNode;

import java.io.*;
import java.util.*;

public class HuffmanDecompressor implements IHuffmanDecompress {

    Scanner fileName = new Scanner(System.in);
    int t;
    @Override
    public String decompress(File fileObj, TreeNode root,int extraBits) {
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

            inorderTraversal(treeRoot);

            extra_bits = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Extra bits:"+extra_bits);

//            int val = fileReader.read();
            int val = bufferedReader.read();
            StringBuilder binaryCode = new StringBuilder();
            char character;

            while(val!=-1){
                character = (char) val;

                String binaryEqui = getBinaryFromChar(character);

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

    public TreeNode deserialize(String nodeList){
        if(nodeList == null)
            return null;
        t = 0;
        String[] arr = nodeList.split(",");
        for(String x:arr){
            System.out.print(x+"\t");
        }
        System.out.println();
        return treeBuilder(arr);
    }

    public TreeNode treeBuilder(String[] arr){
        if(arr[t].equals("#"))
            return null;

        TreeNode root;

        if(arr[t].equals("$"))
            root = new TreeNode(arr[t].charAt(0),0);
        else
            root = new TreeNode((char)Integer.parseInt(arr[t]),0);

        t++;
        root.left = treeBuilder(arr);
        t++;
        root.right = treeBuilder(arr);

        return root;
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

    private void inorderTraversal(TreeNode root) {
        if (root == null)
            return;

        inorderTraversal(root.left);
        System.out.print(root.getChar() + "\t");
        inorderTraversal(root.right);
    }
}
