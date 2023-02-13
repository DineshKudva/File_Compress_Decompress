package com.cap_ex.auxiliary;

import com.cap_ex.decompression.FileContents;
import com.cap_ex.decompression.IFileContents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GeneralMethods implements IGeneralMethods {
    @Override
    public char getAscii(String curCode) {
        int binaryNum = Integer.parseInt(curCode);
        int deciVal = 0;

        for (int i = 0; i < curCode.length(); i++) {
            deciVal += (binaryNum % 10) * (int) Math.pow(2, i);
            binaryNum /= 10;
        }
        return (char) deciVal;
    }

    @Override
    public String serialize(TreeNode root) {
        if (root == null)
            return null;

        Stack<TreeNode> nodeStack = new Stack<>();
        nodeStack.push(root);

        List<String> charList = new ArrayList<>();
        while (!nodeStack.empty()) {

            TreeNode node = nodeStack.pop();

            if (node == null)
                charList.add("#");
            else {
                if (node.getRightChild() == null && node.getLeftChild() == null)
                    charList.add("" + node.getAsciiVal());
                else
                    charList.add("$");
                nodeStack.push(node.getRightChild());
                nodeStack.push(node.getLeftChild());
            }

        }

        return String.join(",", charList);
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

        while (result.length() != 8)
            result = '0' + result;

        return result;
    }

    @Override
    public String getBinaryFromInt(int decival) {

        if (decival < 0)
            throw new ArithmeticException();

        String result = "";

        while (decival != 0) {
            int rem = decival % 2;
            decival /= 2;
            result = rem + result;
        }

        while (result.length() != 8)
            result = '0' + result;

        return result;
    }

    @Override
    public IFileContents extractContents(File fileObj) {

        IFileContents fileContents;

        try {

            FileInputStream fin = new FileInputStream(fileObj);
            ObjectInputStream obj = new ObjectInputStream(fin);

            fileContents = new FileContents();

            fileContents.setHuffTree((String) obj.readObject());
            fileContents.setExtraBits(obj.readInt());
            fileContents.setByteArray((byte[]) obj.readObject());

            obj.close();
            fin.close();


        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return fileContents;

    }

    public void getStats(String path1, String path2) {
        File file1 = new File(path1);
        File file2 = new File(path2);

        System.out.println("Size of original file:" + file1.length() / 1024 + " Kb");
        System.out.println("Size of compressed file:" + file2.length() / 1024 + " Kb");
        float compRate = (float) (file1.length() - file2.length()) * 100 / file1.length();

        System.out.println("Compression rate:" + compRate + "%");

    }


}
