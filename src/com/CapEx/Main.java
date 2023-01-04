package com.CapEx;

import java.io.*;
import java.util.*;
import java.util.logging.*;


public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    static FileHandler fileHandler;

    public static int size_of_compressed_string = 0;
    public static HashMap<Character, Integer> freqTable = new HashMap<>();
    public static PriorityQueue<TreeNode> nodeQueue = new PriorityQueue<TreeNode>(new CharComparator());
    public static HashMap<Character, String> characterCodes = new HashMap<>();

    public static ArrayList<TreeNode> nodeArrayList = new ArrayList<>();

    public static TreeNode[] nodeList;

    public static void generateFrequency(String data) {

        for (char ch : data.toCharArray())
            freqTable.put(ch, freqTable.getOrDefault(ch, 0) + 1);


        buildNodeList();

    }

    public static void buildNodeList() {
        nodeList = new TreeNode[freqTable.size()];
        int i = 0;

        for (Map.Entry<Character, Integer> mapElement : freqTable.entrySet()) {
            char ch = mapElement.getKey();
            int freq = mapElement.getValue();

            TreeNode newNode = new TreeNode(ch, freq);
            nodeList[i++] = newNode;
        }

        Arrays.sort(nodeList, new CharComparator());

        for(TreeNode x:nodeList){
            nodeArrayList.add(x);
        }

//        System.out.println("Node List:");
//        System.out.println("Char\tFreq\tAscii");
//        for(TreeNode temp:nodeList){
//            System.out.println(temp.getChar()+"\t("+temp.getFreq()+")"+"\t["+temp.getAscii()+"]");
//        }


    }

    public static TreeNode buildTree(String data) {

        for (TreeNode temp : nodeList)
            nodeQueue.add(temp);


//        while (!(nodeQueue.size() == 1)) {
          while(!(nodeArrayList.size() == 1)){
            logger.info("Queue so far:");


//            System.out.println("\nQueue so far");
//            for(TreeNode temp: nodeArrayList){
//                System.out.print(temp.getChar()+"("+temp.getFreq()+")"+"["+temp.getAscii()+"]\t\t");
//            }

//            TreeNode leftNode = nodeQueue.poll();
//            TreeNode rightNode = nodeQueue.poll();

            TreeNode leftNode = nodeArrayList.get(0);

            nodeArrayList.remove(0);

            TreeNode rightNode = nodeArrayList.get(0);
            nodeArrayList.remove(0);

            TreeNode resultNode = new TreeNode('$', leftNode.getFreq() + rightNode.getFreq());

            resultNode.left = leftNode;
            resultNode.right = rightNode;
            int a = leftNode.getAscii();
            int b = rightNode.getAscii();

            resultNode.addAsciiVal(a,b);

//            nodeQueue.add(resultNode);
            nodeArrayList.add(resultNode);

            Collections.sort(nodeArrayList,new CharComparator());

        }

//        System.out.println("\nQueue so far");
//        for(TreeNode temp:nodeArrayList){
//            System.out.print(temp.getChar()+"("+temp.getFreq()+")"+"["+temp.getAscii()+"]\t\t");
//        }


        return nodeArrayList.get(0);
//        return nodeQueue.peek();
    }

    public static void buildCode(TreeNode root, String code) {
        if (root == null)
            return;
        else if (root.left == null && root.right == null) {
            characterCodes.put(root.getChar(), code.length() > 0 ? code : "1");
            return;
        }

        buildCode(root.left, code + '0');
        buildCode(root.right, code + '1');
    }

    public static void main(String args[]) {

        try {
            fileHandler = new FileHandler("MyLog.log", false);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sourceData = "sentence 1.\nsentence 2.\nsentence 3.";
        String compressedData = "";


        generateFrequency(sourceData);

        for (Map.Entry<Character, Integer> mapElement : freqTable.entrySet()) {
            char ch = mapElement.getKey();
            int freq = mapElement.getValue();
            logger.info("Character :" + ch + "\t Frequency:" + freq);
        }

        TreeNode root = buildTree(sourceData);

        System.out.println("\nInorder Traversal");
        inOrderTraverse(root);
        System.out.println();

        buildCode(root, "");

        compressedData = dataCompression(sourceData);

        for (Map.Entry<Character, String> mapEle : characterCodes.entrySet()) {
            char ch = mapEle.getKey();
            String code = mapEle.getValue();
//            System.out.println("Character :" + ch + "\t Code:" + code);
            logger.info("Character :" + ch + "\t Code:" + code);
        }


        int size1 = sourceData.length() * 8;
        int size2 = compressedData.length() * 8;

        logger.info("we went from storing " + size1 + " bits of data to " + size2 + " bits. That's a " + ((float) (size1 - size2) * 100 / (float) size1) + " % decrease!!");


        String uncompressedData = "";

        uncompressedData = decompressData(compressedData, uncompressedData, root);

//        System.out.println("Source data:" + sourceData);
//        System.out.println("Compressed data :" + compressedData);
//        System.out.println("Decompressed data:" + uncompressedData);

        logger.info("Source data:" + sourceData);
        logger.info("Compressed data :" + compressedData);
        logger.info("Decompressed data:" + uncompressedData);

        logger.info("obtained uncompressed data:" + uncompressedData);

        if (uncompressedData.equals(sourceData)) {
            logger.info("Compression-Decompression carried out successfully");
        }

    }

    private static void inOrderTraverse(TreeNode root) {
        if(root == null)
            return;

        inOrderTraverse(root.left);
        System.out.print(root.getChar()+"\t");
        inOrderTraverse(root.right);
    }



    private static String getBinaryFromChar(char ch) {
        int deci_val = (int) ch;
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

    private static String dataCompression(String sourceData) {
        // TODO Auto-generated method stub
        String compressedData = "";
        String curCode = "";
        String rem = "";

        for (char ch : sourceData.toCharArray()) {
            String presentCode = characterCodes.get(ch);

//            System.out.println("Character:"+ch+"\tPresent Code:"+presentCode);

            curCode += presentCode;

//            System.out.println("\tCurrent Code:"+curCode);

            if (curCode.length() >= 7) {
                rem = curCode.substring(7);
                curCode = curCode.substring(0, 7);
//                System.out.println("\tRemaining:"+rem);
            }

            if (curCode.length() == 7) {

                char characterObtained = getAscii(curCode);
                compressedData += characterObtained;
                size_of_compressed_string += 7;
//                System.out.println("\tCode Cut down:"+curCode+"\tCharacter :"+characterObtained);

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
//            System.out.println("***Finally***\tCode Cut down:"+curCode+"\tCharacter :"+remCharacter);
            compressedData += remCharacter;
        }

        return compressedData;
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


    private static String getBinary(int num) {

        StringBuilder binaryStr = new StringBuilder();

        while (num != 0) {
            binaryStr.append(num % 2);
            num /= 2;
        }

        while (binaryStr.length() != 7)
            binaryStr.append(0);

        binaryStr.reverse();

        return binaryStr.toString();
    }

    //  ----------------- Decompression of data ------------
    private static String decompressData(String compressedData, String uncompressedData, TreeNode root) {

        String binaryStr = "";

        for (char ch : compressedData.toCharArray()) {
            String binary_val = getBinaryFromChar(ch);
//            System.out.println("Character:"+ch+"\tBinary Value:"+binary_val);
            binaryStr += binary_val;
        }

        binaryStr = binaryStr.substring(0,size_of_compressed_string);
        TreeNode temp = root;
        String code = "";

        for (char ch : binaryStr.toCharArray()) {
            if (ch == '1') {
                code += '1';
                temp = temp.right;
            }
            else {
                code += '0';
                temp = temp.left;
            }

            if (temp.left == null && temp.right == null) {
//                System.out.println("------Reached leaf-----\n\tCharacter:"+temp.getChar()+"\tCode:"+code);
                uncompressedData += temp.getChar();
                temp = root;
                code = "";
            }
        }

        return uncompressedData;
    }


}