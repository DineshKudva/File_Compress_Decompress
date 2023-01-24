package com.cap_ex.auxiliary;

import com.cap_ex.TreeNode;

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
                if(node.right ==null && node.left==null)
                    charList.add(""+node.getAscii());
                else
                    charList.add("$");
                nodeStack.push(node.right);
                nodeStack.push(node.left);
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

        while (result.length() != 7)
            result = '0' + result;

        return result;
    }


}
