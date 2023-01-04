package com.CapEx;

import java.io.*;
import java.util.*;
import java.util.logging.*;


public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    static FileHandler fileHandler;


    public static HashMap<Character, Integer> freqTable = new HashMap<>();
    public static PriorityQueue<TreeNode> nodeQueue = new PriorityQueue<TreeNode>(new CharComparator());
    public static HashMap<Character, String> characterCodes = new HashMap<>();

    public static TreeNode[] nodeList;

    public static void generateFrequency(String data) {

        for (char ch : data.toCharArray())
            freqTable.put(ch, freqTable.getOrDefault(ch, 0) + 1);


        buildNodeList();

    }

    public static void buildNodeList() {
        nodeList = new TreeNode[freqTable.size()];
        int i=0;

        for (Map.Entry<Character, Integer> mapElement : freqTable.entrySet()) {
            char ch = mapElement.getKey();
            int freq = mapElement.getValue();

            TreeNode newNode = new TreeNode(ch, freq);
            nodeList[i++] = newNode;
        }

        Arrays.sort(nodeList, new CharComparator());

    }

    public static TreeNode buildTree(String data) {

//		for (Map.Entry<Character, Integer> mapElement : freqTable.entrySet()) {
//			char ch = mapElement.getKey();
//			int freq = mapElement.getValue();
//
//			com.CapEx.TreeNode newNode = new com.CapEx.TreeNode(ch, freq);
//			nodeQueue.add(newNode);
//
//
//		}

        

        for(TreeNode temp : nodeList)
            nodeQueue.add(temp);



        while (!(nodeQueue.size() == 1)) {

//            System.out.println("Queue so far:");

            logger.info("Queue so far:");

            for(TreeNode temp : nodeQueue) {
//                System.out.print(temp.getChar()+"("+temp.getFreq()+")"+"\t");
            }
//            System.out.println();

            TreeNode leftNode = nodeQueue.poll();
            TreeNode rightNode = nodeQueue.poll();

            TreeNode resultNode = new TreeNode('$', leftNode.getFreq() + rightNode.getFreq());

            resultNode.left = leftNode;
            resultNode.right = rightNode;

            nodeQueue.add(resultNode);



        }

        return nodeQueue.peek();
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
             fileHandler = new FileHandler("MyLog.log",false);
             logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sourceData = "a red racecar";
        String compressedData = "";



        generateFrequency(sourceData);

		for(Map.Entry<Character, Integer> mapElement : freqTable.entrySet()) {
			char ch = mapElement.getKey();
			int freq = mapElement.getValue();

//			System.out.println("Character :"+ch+"\t Frequency:"+freq);
            logger.info("Character :"+ch+"\t Frequency:"+freq);
		}

        TreeNode root = buildTree(sourceData);
        buildCode(root, "");

        compressedData = dataCompression(sourceData);

		for (Map.Entry<Character, String> mapEle : characterCodes.entrySet()) {
			char ch = mapEle.getKey();
			String code = mapEle.getValue();

//			System.out.println("Character :" + ch + "\t Code:" + code);
            logger.info("Character :" + ch + "\t Code:" + code);
		}


//		for(char ch:compressedData.toCharArray()) {
//			System.out.print("Decimal Value : "+(int)ch);
//			System.out.println("\tBinary equivalent : "+getBinary((int)ch));
//		}

		int size1 = sourceData.length()*8;
		int size2 = compressedData.length()*8;

//		System.out.println("we went from storing "+size1+" bits of data to "+size2+" bits. That's a "+((float)(size1-size2)*100/(float)size1)+" % decrease!!");
        logger.info("we went from storing "+size1+" bits of data to "+size2+" bits. That's a "+((float)(size1-size2)*100/(float)size1)+" % decrease!!");


        String uncompressedData = "";

        uncompressedData = decompressData(compressedData,uncompressedData,root);

        System.out.println("Source data:"+sourceData);
        System.out.println("Compressed data :"+compressedData);
        System.out.println("Decompressed data:"+uncompressedData);

        logger.info("obtained uncompressed data:"+uncompressedData);

        if(uncompressedData.equals(sourceData)){
            logger.info("Compression-Decompression carried out successfully");
        }

    }

    private static String decompressData(String compressedData, String uncompressedData,TreeNode root) {

        String binaryStr = "";

        for(char ch: compressedData.toCharArray()){
            String binary_val = getBinaryFromChar(ch);
//            String binary_val = getBinary((int)ch);
            binaryStr += binary_val;
        }


        TreeNode temp = root;

        for(char ch:binaryStr.toCharArray()){
            if(ch == '1')
                temp = temp.right;
            else
                temp = temp.left;

            if(temp.left == null && temp.right ==null){
                uncompressedData += temp.getChar();
                temp = root;
            }
        }

        return uncompressedData;
    }

    private static String getBinaryFromChar(char ch) {
        int deci_val = (int) ch;
        String result = "";

        while(deci_val!=0){
            int rem = deci_val%2;
            deci_val /= 2;
            result = rem + result;
        }

        while(result.length()!=7)
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

//			System.out.println("Character : "+ch+"\tCode : "+presentCode);

            curCode += presentCode;
//			System.out.println("Presently : "+curCode);

            if (curCode.length() > 7) {
                rem = curCode.substring(7);
                curCode = curCode.substring(0, 7);
            }

            if (curCode.length() == 7) {
//				System.out.println("Entering get ascii with :\t"+curCode);
//				System.out.println("Remaining bits : "+rem);
                char characterObtained = getAscii(curCode);
                compressedData += characterObtained;
                curCode = "";
            }
            else if(curCode.length()<7)
                continue;


            curCode = rem;

        }

        if(!curCode.equals("")) {
            int x = 7 - curCode.length();
            for(int i=0;i<x;i++)
                curCode += '0';
            char remCharacter = getAscii(curCode);
            compressedData += remCharacter;
        }

        return compressedData;
    }

    private static char getAscii(String curCode) {
        int binaryNum = Integer.parseInt(curCode);
        int deciVal = 0;

//		System.out.print("Binary Number:"+binaryNum);

        for (int i = 0; i < curCode.length(); i++) {
            deciVal += (binaryNum % 10) * (int)Math.pow(2, i);
            binaryNum /= 10;
        }


//		System.out.println("\t\tDecimal Equivalent:"+deciVal+"\n------------------");

        return (char) deciVal;
    }


    private static String getBinary(int num) {

        StringBuilder binaryStr = new StringBuilder();

        while(num!=0) {
            binaryStr.append(num%2);
            num/=2;
        }

        while(binaryStr.length()!=7)
            binaryStr.append(0);

        binaryStr.reverse();

        return binaryStr.toString();
    }


}