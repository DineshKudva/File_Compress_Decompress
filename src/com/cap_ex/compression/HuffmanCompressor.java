package com.cap_ex.compression;


import com.cap_ex.auxiliary.*;

import java.io.*;
import java.util.*;

public class HuffmanCompressor implements IHuffmanCompress {

    Scanner fileName = new Scanner(System.in);
    public int extraBits;

    IGeneralMethods method = new GeneralMethods();

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
    public Queue<TreeNode> buildNodeQueue(Map<Character, Integer> freqTable) {
        Queue<TreeNode> nodeQueue = new PriorityQueue<>(new CharComparator());

        for(Map.Entry<Character, Integer> mapEle : freqTable.entrySet()){
            char character = mapEle.getKey();
            int freq = mapEle.getValue();

            TreeNode node = new TreeNode(character,freq,null,null);

            nodeQueue.add(node);
        }

        return nodeQueue;
    }

    @Override
    public TreeNode buildTree(Queue<TreeNode> nodeQueue) {
        while (nodeQueue.size() != 1) {


            TreeNode leftNode = nodeQueue.poll();
            TreeNode rightNode = nodeQueue.poll();

            TreeNode resultNode = new TreeNode('$', leftNode.getFreq() + rightNode.getFreq(),leftNode,rightNode);

            resultNode.addAsciiVal(leftNode.getAsciiVal(), rightNode.getAsciiVal());

            nodeQueue.add(resultNode);

        }

        return nodeQueue.poll();
    }

    @Override
    public Map<Character, String> getCodes(TreeNode root) {
        Map<Character, String> characterCodes = new HashMap<>();

        buildCode(root,"",characterCodes);

        return characterCodes;
    }

    public static void buildCode(TreeNode root, String code, Map<Character, String> characterCodes) {
        if (root == null)
            return;
        else if (root.getLeftChild() == null && root.getRightChild() == null) {
            characterCodes.put(root.getChar(), code.length() > 0 ? code : "1");
            return;
        }

        buildCode(root.getLeftChild(), code + '0',characterCodes);
        buildCode(root.getRightChild(), code + '1',characterCodes);
    }

    @Override
    public String compress(Map<Character,String> characterCodes, File fileObj, TreeNode root) {

        System.out.println("Enter name for compressed file:(without any extensions)");
        String outputFilePath = fileName.nextLine();

        outputFilePath = "/C:/Users/Dinesh/Desktop/"+outputFilePath+".txt";
        File newFile = new File(outputFilePath);
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
            PrintWriter pw = new PrintWriter(fw);

            String serializedTree = method.serialize(root);
            String remBits = Integer.toString(extraBits);

            pw.println(serializedTree);
            pw.println(remBits);
            pw.println(compressedString);

            pw.close();
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Size of compressed file:"+newFile.length()/1024+" Kb");
        float compRate = (fileObj.length() - newFile.length())*100/ fileObj.length();
        System.out.println("Compression rate:"+compRate+"%");

        return outputFilePath;
    }

    @Override
    public String dataCompression(String encodedBits,Map<Character,String> characterCodes) {
        StringBuilder compressedData = new StringBuilder();

        int i=0;

        int iterations = (encodedBits.length()/7);

        for(int j=0;j<iterations;j++){
            String curCode = encodedBits.substring(i,i+7);
            char equiChar = method.getAscii(curCode);

            compressedData.append(equiChar);

            i+=7;

        }


        extraBits = 7 - (encodedBits.length()%7);
        String endingCode = encodedBits.substring(i,encodedBits.length());
        for(int j=0;j<extraBits;j++)
            endingCode += '0';

        compressedData.append(method.getAscii(endingCode));
        return compressedData.toString();
    }

}
