package com.cap_ex.auxiliary;

import java.io.Serializable;

public class TreeNode{
    private Character ch;
    private Integer frequency;
    private Integer asciiVal;

    private TreeNode left;
    private TreeNode right;

    public TreeNode(char a, int b,TreeNode left,TreeNode right) {

        this.ch = a;
        this.frequency = b;
        this.asciiVal = (int) a;
        this.left = left;
        this.right = right;

    }

    public char getChar() {
        return this.ch.charValue();
    }

    public int getFreq() {
        return this.frequency;
    }

    public int getAsciiVal() {
        return this.asciiVal;
    }

    public void addAsciiVal(int a,int b){
        this.asciiVal = a+b;
    }

    public TreeNode getLeftChild(){
        return this.left;
    }

    public TreeNode getRightChild(){
        return this.right;
    }

    public void setLeft(TreeNode node){
        this.left = node;
    }

    public void setRight(TreeNode node){
        this.right = node;
    }

}
