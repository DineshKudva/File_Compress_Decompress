package com.CapEx;

public class TreeNode {
    private Character ch;
    private Integer frequency;
    private Integer asciiVal;

    TreeNode left;
    TreeNode right;

    TreeNode(char a, int b) {

        this.ch = a;
        this.frequency = b;
        this.asciiVal = (int) a;
        this.left = null;
        this.right = null;

    }

    public char getChar() {
        return this.ch.charValue();
    }

    public int getFreq() {
        return this.frequency;
    }

    public int getAscii() {
        return this.asciiVal;
    }

    public void addAsciiVal(int a,int b){
        this.asciiVal = a+b;
    }
}
