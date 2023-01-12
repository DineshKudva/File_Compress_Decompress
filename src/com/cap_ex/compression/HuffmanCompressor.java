package com.cap_ex.compression;


import com.cap_ex.CharComparator;
import com.cap_ex.TreeNode;

import java.io.*;
import java.util.*;

public class HuffmanCompressor implements IHuffmanCompress {

    @Override
    public Map<Character, Integer> generateFrequency(File fileObj) {

        Map<Character,Integer> freqTable = new HashMap<>();

        try{
            Scanner fileScanner = new Scanner(fileObj);
            while(fileScanner.hasNext()){
                String data = fileScanner.nextLine();

                if(fileScanner.hasNext())
                    data += '\n';

                for(char character : data.toCharArray())
                    freqTable.put(character, freqTable.getOrDefault(character, 0) + 1);
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
        while (!(nodeList.size() == 1)) {


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
    public String compress(Map<Character,String> characterCodes,String inputFilePath) {
        return null;
    }
}
