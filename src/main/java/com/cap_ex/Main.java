package com.cap_ex;

import com.cap_ex.auxiliary.CharComparator;
import com.cap_ex.auxiliary.TreeNode;

import java.io.*;
import java.util.*;
import java.util.logging.*;


public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    static FileHandler fileHandler;

    public static int size_of_compressed_string = 0;

    public static HashMap<Character, Integer> freqTable = new HashMap<>();

    public static HashMap<Character, String> characterCodes = new HashMap<>();

    public static ArrayList<TreeNode> nodeArrayList = new ArrayList<>();

    public static void generateFrequency(String data) {

        for (char ch : data.toCharArray())
            freqTable.put(ch, freqTable.getOrDefault(ch, 0) + 1);


        buildNodeList();

    }

    public static void buildNodeList() {

        for (Map.Entry<Character, Integer> mapElement : freqTable.entrySet()) {
            char ch = mapElement.getKey();
            int freq = mapElement.getValue();

            TreeNode newNode = new TreeNode(ch, freq,null,null);
            nodeArrayList.add(newNode);
        }

        Collections.sort(nodeArrayList, new CharComparator());

    }

    public static TreeNode buildTree() {


        while (!(nodeArrayList.size() == 1)) {
            logger.info("Queue so far:");


            TreeNode leftNode = nodeArrayList.get(0);

            nodeArrayList.remove(0);

            TreeNode rightNode = nodeArrayList.get(0);
            nodeArrayList.remove(0);

            TreeNode resultNode = new TreeNode('$', leftNode.getFreq() + rightNode.getFreq(),leftNode,rightNode);

            int a = leftNode.getAsciiVal();
            int b = rightNode.getAsciiVal();

            resultNode.addAsciiVal(a, b);

            nodeArrayList.add(resultNode);

            Collections.sort(nodeArrayList, new CharComparator());

        }

        return nodeArrayList.get(0);
    }

    public static void buildCode(TreeNode root, String code) {
        if (root == null)
            return;
        else if (root.getLeftChild()== null && root.getRightChild()== null) {
            characterCodes.put(root.getChar(), code.length() > 0 ? code : "1");
            return;
        }

        buildCode(root.getLeftChild(), code + '0');
        buildCode(root.getRightChild(), code + '1');
    }

    public static void main(String args[]) {

        try {
            fileHandler = new FileHandler("MyLog.log", false);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sourceData = "She sells seashells at the seashore.";
        String compressedData = "";


        generateFrequency(sourceData);

        for (Map.Entry<Character, Integer> mapElement : freqTable.entrySet()) {
            char ch = mapElement.getKey();
            int freq = mapElement.getValue();
            logger.info("Character : {} \t" + ch + "\t Frequency:" + freq);
        }

        TreeNode root = buildTree();

        System.out.println("\nInorder Traversal");

        inOrderTraverse(root);
        System.out.println();

        buildCode(root, "");

        compressedData = dataCompression(sourceData);

        computeSize();

        for (Map.Entry<Character, String> mapEle : characterCodes.entrySet()) {
            char ch = mapEle.getKey();
            String code = mapEle.getValue();
//            logger.info("Character :" + ch + "\t Code:" + code);

            System.out.println("Character :" + ch + "\t Code:" + code);
        }


        int size1 = sourceData.length() * 8;
        int size2 = compressedData.length() * 8;


        logger.info("we went from storing " + size1 + " bits of data to " + size2 + " bits. That's a " + ((float) (size1 - size2) * 100 / size1) + " % decrease!!");


        String uncompressedData = "";

        uncompressedData = decompressData(compressedData, root);

        logger.info("Source data:" + sourceData);
        logger.info("Compressed data :" + compressedData);
        logger.info("Decompressed data:" + uncompressedData);

        logger.info("obtained uncompressed data:" + uncompressedData);

        System.out.println("Compressed data :"+compressedData);

        if (uncompressedData.equals(sourceData)) {
            logger.info("Compression-Decompression carried out successfully");
        }

    }

    private static void inOrderTraverse(TreeNode root) {
        if (root == null)
            return;

        inOrderTraverse(root.getLeftChild());
        System.out.print(root.getChar() + "\t");
        inOrderTraverse(root.getRightChild());


    }

    private static String getBinaryFromChar(char ch) {
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


    private static char getAscii(String curCode) {
        int binaryNum = Integer.parseInt(curCode);
        int deciVal = 0;

        for (int i = 0; i < curCode.length(); i++) {
            deciVal += (binaryNum % 10) * (int) Math.pow(2, i);
            binaryNum /= 10;
        }


        return (char) deciVal;
    }

    //    --------------------------Compression of Data--------------------------
    private static String dataCompression(String sourceData) {
        String compressedData = "";
        String curCode = "";
        String rem = "";

        for (char ch : sourceData.toCharArray()) {
            String presentCode = characterCodes.get(ch);


            curCode += presentCode;

            if (curCode.length() >= 7) {
                rem = curCode.substring(7);
                curCode = curCode.substring(0, 7);
            }

            if (curCode.length() == 7) {

                char characterObtained = getAscii(curCode);
                compressedData += characterObtained;
                size_of_compressed_string += 7;

            } else if (curCode.length() < 7)
                continue;


            curCode = rem;

        }

        if (!curCode.equals("")) {
            size_of_compressed_string += curCode.length();
            int x = 7 - curCode.length();
            for (int i = 0; i < x; i++)
                curCode += "0";

            char remCharacter = getAscii(curCode);
            compressedData += remCharacter;
        }

        System.out.println("Size of compressed data : "+size_of_compressed_string);

        return compressedData;
    }


    public static void computeSize(){
        int size = 0;

        for(Map.Entry<Character,Integer> mapEle : freqTable.entrySet()){
            char ch = mapEle.getKey();
            int freq = mapEle.getValue();
            int bitSize = characterCodes.get(ch).length();

            size += (freq*bitSize);

        }

        System.out.println("Size called in main:"+size);
    }


    //  ----------------- Decompression of data ------------
    private static String decompressData(String compressedData, TreeNode root) {

        String binaryStr = "";
        StringBuilder uncompressedData = new StringBuilder();

        for (char ch : compressedData.toCharArray()) {
            String binary_val = getBinaryFromChar(ch);
            binaryStr += binary_val;
        }

        binaryStr = binaryStr.substring(0, size_of_compressed_string);
        TreeNode temp = root;

        for (char ch : binaryStr.toCharArray()) {

            if (temp.getLeftChild()== null && temp.getRightChild()== null) {
                uncompressedData.append(temp.getChar());
                temp = root;
                continue;
            }

            if (ch == '1')
                temp = temp.getRightChild();
            else
                temp = temp.getLeftChild();


            if (temp.getLeftChild()== null && temp.getRightChild()== null) {
                uncompressedData.append(temp.getChar());
                temp = root;
            }
        }

        return uncompressedData.toString();
    }


}