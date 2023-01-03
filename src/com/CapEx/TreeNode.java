package com.CapEx;

public class TreeNode {
    private Character ch;
    private Integer frequency;

    TreeNode left;
    TreeNode right;

    TreeNode(char a,int b){

        this.ch = a;
        this.frequency = b;
        this.left = null;
        this.right = null;

    }

    public char getChar() {
        return this.ch.charValue();
    }

    public int getFreq() {
        return this.frequency;
    }
}
