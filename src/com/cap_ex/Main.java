package com.cap_ex;

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

            TreeNode newNode = new TreeNode(ch, freq);
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

            TreeNode resultNode = new TreeNode('$', leftNode.getFreq() + rightNode.getFreq());

            resultNode.left = leftNode;
            resultNode.right = rightNode;
            int a = leftNode.getAscii();
            int b = rightNode.getAscii();

            resultNode.addAsciiVal(a, b);

            nodeArrayList.add(resultNode);

            Collections.sort(nodeArrayList, new CharComparator());

        }

        return nodeArrayList.get(0);
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

        String sourceData = "TXT test file\n" +
                "Purpose: Provide example of this file type\n" +
                "Document file type: TXT\n" +
                "Version: 1.0\n" +
                "Remark:\n" +
                "\n" +
                "Example content:\n" +
                "The names \"John Doe\" for males, \"Jane Doe\" or \"Jane Roe\" for females, or \"Jonnie Doe\" and \"Janie Doe\" for children, or just \"Doe\" non-gender-specifically are used as placeholder names for a party whose true identity is unknown or must be withheld in a legal action, case, or discussion. The names are also used to refer to acorpse or hospital patient whose identity is unknown. This practice is widely used in the United States and Canada, but is rarely used in other English-speaking countries including the United Kingdom itself, from where the use of \"John Doe\" in a legal context originates. The names Joe Bloggs or John Smith are used in the UK instead, as well as in Australia and New Zealand.\n" +
                "\n" +
                "John Doe is sometimes used to refer to a typical male in other contexts as well, in a similar manner to John Q. Public, known in Great Britain as Joe Public, John Smith or Joe Bloggs. For example, the first name listed on a form is often John Doe, along with a fictional address or other fictional information to provide an example of how to fill in the form. The name is also used frequently in popular culture, for example in the Frank Capra film Meet John Doe. John Doe was also the name of a 2002 American television series.\n" +
                "\n" +
                "Similarly, a child or baby whose identity is unknown may be referred to as Baby Doe. A notorious murder case in Kansas City, Missouri, referred to the baby victim as Precious Doe. Other unidentified female murder victims are Cali Doe and Princess Doe. Additional persons may be called James Doe, Judy Doe, etc. However, to avoid possible confusion, if two anonymous or unknown parties are cited in a specific case or action, the surnames Doe and Roe may be used simultaneously; for example, \"John Doe v. Jane Roe\". If several anonymous parties are referenced, they may simply be labelled John Doe #1, John Doe #2, etc. (the U.S. Operation Delego cited 21 (numbered) \"John Doe\"s) or labelled with other variants of Doe / Roe / Poe / etc. Other early alternatives such as John Stiles and Richard Miles are now rarely used, and Mary Major has been used in some American federal cases.\n" +
                "\n" +
                "\n" +
                "\n" +
                "File created by https://www.online-convert.com\n" +
                "More example files: https://www.online-convert.com/file-type\n" +
                "Text of Example content: Wikipedia (https://en.wikipedia.org/wiki/John_Doe)\n" +
                "License: Attribution-ShareAlike 4.0 (https://creativecommons.org/licenses/by-sa/4.0/)\n" +
                "\n" +
                "Feel free to use and share the file according to the license above.";
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

        inOrderTraverse(root.left);
        System.out.print(root.getChar() + "\t");
        inOrderTraverse(root.right);


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


}