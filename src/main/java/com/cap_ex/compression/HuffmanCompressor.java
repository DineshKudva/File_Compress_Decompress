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
            throw new RuntimeException();
        }


        return freqTable;
    }

    @Override
    public Queue<TreeNode> buildNodeQueue(Map<Character, Integer> freqTable) {

        if(freqTable == null)
            throw new NullPointerException();

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
    public void getCodes(TreeNode root,String code,Map<Character,String> characterCodes) {
        if (root == null)
            return;
        else if (root.getLeftChild() == null && root.getRightChild() == null) {
            characterCodes.put(root.getChar(), code.length() > 0 ? code : "1");
            return;
        }

        getCodes(root.getLeftChild(), code + '0',characterCodes);
        getCodes(root.getRightChild(), code + '1',characterCodes);
    }



    @Override
    public String compress(Map<Character,String> characterCodes, File fileObj, TreeNode root,int size) {

        try {
            FileReader fileScanner = new FileReader(fileObj);

//            System.out.println("Enter name for compressed file:(without any extensions)");
//        String outputFilePath = fileName.nextLine();

//        outputFilePath = "src/textFiles/compressedFiles/"+outputFilePath+".txt";
            String outputFilePath = "src/textFiles/compressedFiles/compressed.txt";
            File newFile = new File(outputFilePath);

            byte[] byteArray = new byte[size];
            String curCode= "";
            int idx = 0;

            int val = fileScanner.read();
            char character;

            while(val!=-1){
                character = (char) val;

                curCode += characterCodes.get(character);

                if(curCode.length()<8){
                    val = fileScanner.read();
                    continue;
                }
                else{
                    while(curCode.length()>8){
                        byte curByte =(byte) Integer.parseInt(curCode.substring(0,8),2);
                        curCode = curCode.substring(8,curCode.length());
                        byteArray[idx++] = curByte;
                    }
                }

                val=fileScanner.read();
            }


            fileScanner.close();

            if(!curCode.equals("")){
                extraBits = 8 - curCode.length();
                for(int i=0;i<extraBits;i++)
                    curCode += '0';
                byteArray[idx] = (byte) Integer.parseInt(curCode.substring(0,8),2);
            }


            FileOutputStream fout = new FileOutputStream(outputFilePath);
            ObjectOutputStream obj = new ObjectOutputStream(fout);

            String serializedTree = method.serialize(root);

            obj.writeObject(serializedTree);
            obj.writeInt(extraBits);
            obj.writeObject(byteArray);

            obj.flush();

            obj.close();
            fout.close();

            System.out.println("Size of original file:"+fileObj.length()/1024+" Kb");
            System.out.println("Size of compressed file:"+newFile.length()/1024+" Kb");
            float compRate = (fileObj.length() - newFile.length())*100/ fileObj.length();
            System.out.println("Compression rate:"+compRate+"%");

            return outputFilePath;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public int getArraySize(Map<Character, String> charMap, Map<Character, Integer> freqMap) {
        int size =0;
        for(Map.Entry<Character,String> mapEle : charMap.entrySet()){
            char ch = mapEle.getKey();
            int len = mapEle.getValue().length();
            int freq = freqMap.get(ch);

            size += (len*freq);
        }

        if(size%8 ==0)
            size /= 8;
        else
            size = (size/8) + 1;

        return size;
    }


}
