package com.cap_ex.auxiliary;

import java.util.*;

public class GeneralMethods implements IGeneralMethods{
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
        if(root == null)
            return null;

        Stack<TreeNode> nodeStack = new Stack<>();
        nodeStack.push(root);

        List<String> charList = new ArrayList<>();
        while(!nodeStack.empty()){

            TreeNode node = nodeStack.pop();

            if(node == null)
                charList.add("#");
            else{
                if(node.getRightChild() ==null && node.getLeftChild()==null)
                    charList.add(""+node.getAsciiVal());
                else
                    charList.add("$");
                nodeStack.push(node.getRightChild());
                nodeStack.push(node.getLeftChild());
            }

        }

        return String.join(",",charList);
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

    @Override
    public String getBinaryFromInt(int decival) {
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


}