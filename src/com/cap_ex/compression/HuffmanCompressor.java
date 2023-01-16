package com.cap_ex.compression;


import com.cap_ex.CharComparator;
import com.cap_ex.TreeNode;

import java.io.*;
import java.util.*;

public class HuffmanCompressor implements IHuffmanCompress {

    Scanner fileName = new Scanner(System.in);
    public static int extraBits;

    @Override
    public Map<Character, Integer> generateFrequency(File fileObj) {

        Map<Character,Integer> freqTable = new HashMap<>();
        try{
            FileReader fileScanner = new FileReader(fileObj);
            int val = fileScanner.read();
            char character;
            while(val!=-1){
                    character = (char) val;
                    freqTable.put(character, freqTable.getOrDefault(character, 0) + 1);
                    val =fileScanner.read();
            }

            fileScanner.close();
        }
        catch(IOException e){
            System.out.println("File not found");
        }


        return freqTable;
    }

    @Override
    public List<TreeNode> buildNodeList(Map<Character, Integer> freqTable) {
        List<TreeNode> nodeList = new ArrayList<>();
        for (Map.Entry<Character, Integer> mapElement : freqTable.entrySet()) {
            char ch = mapElement.getKey();
            int freq = mapElement.getValue();

            TreeNode newNode = new TreeNode(ch, freq);
            nodeList.add(newNode);
        }

        Collections.sort(nodeList, new CharComparator());

        return nodeList;
    }

    @Override
    public TreeNode buildTree(List<TreeNode> nodeList) {
        while (nodeList.size() != 1) {


            TreeNode leftNode = nodeList.get(0);
            nodeList.remove(0);

            TreeNode rightNode = nodeList.get(0);
            nodeList.remove(0);

            TreeNode resultNode = new TreeNode('$', leftNode.getFreq() + rightNode.getFreq());

            resultNode.left = leftNode;
            resultNode.right = rightNode;

            resultNode.addAsciiVal(leftNode.getAscii(), rightNode.getAscii());

            nodeList.add(resultNode);

            Collections.sort(nodeList, new CharComparator());

        }

        return nodeList.get(0);
    }

    @Override
    public Map<Character, String> getCodes(TreeNode root) {
        Map<Character, String> characterCodes = new HashMap<>();

        buildCode(root,"",characterCodes);

        return characterCodes;
    }

    public static void buildCode(TreeNode root, String code,Map<Character, String> characterCodes) {
        if (root == null)
            return;
        else if (root.left == null && root.right == null) {
            characterCodes.put(root.getChar(), code.length() > 0 ? code : "1");
            return;
        }

        buildCode(root.left, code + '0',characterCodes);
        buildCode(root.right, code + '1',characterCodes);
    }

    @Override
    public String compress(Map<Character,String> characterCodes,File fileObj) {

        System.out.println("Enter name for compressed file:(without any extensions)");
        String outputFilePath = fileName.nextLine();

        outputFilePath = "/C:/Users/Dinesh/Desktop/"+outputFilePath+".txt";

        try {
            FileReader fileScanner = new FileReader(fileObj);

            StringBuilder encodedString = new StringBuilder();

            int val = fileScanner.read();
            char character;

            while(val!=-1){
                character = (char) val;
                encodedString.append(characterCodes.get(character));
                val=fileScanner.read();
            }

            fileScanner.close();

            String compressedString = dataCompression(encodedString.toString(), characterCodes);
            FileWriter fw = new FileWriter(outputFilePath);
            fw.write(compressedString);

            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputFilePath;
    }

    @Override
    public String dataCompression(String encodedBits,Map<Character,String> characterCodes) {
        StringBuilder compressedData = new StringBuilder();

        int i=0;

        int iterations = (encodedBits.length()/7);

        for(int j=0;j<iterations;j++){
            String curCode = encodedBits.substring(i,i+7);
            char equiChar = getAscii(curCode);

            compressedData.append(equiChar);

            i+=7;

        }


        extraBits = 7 - (encodedBits.length()%7);
        String endingCode = encodedBits.substring(i,encodedBits.length());
        for(int j=0;j<extraBits;j++)
            endingCode += '0';

        compressedData.append(getAscii(endingCode));
        return compressedData.toString();
    }

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
}
