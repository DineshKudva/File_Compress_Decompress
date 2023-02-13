package com.capexercise.huffmanimplement.auxiliary;

import com.capexercise.huffmanimplement.auxiliary.IFileContents;

public class FileContents implements IFileContents {
    private String huffTree;
    private int extraBits;

    private byte[] byteArray;

    public FileContents() {
        this.huffTree = "";
        this.extraBits = 0;
        this.byteArray = new byte[]{};
    }

    public String getHuffTree() {
        return this.huffTree;
    }

    public int getExtraBits() {
        return this.extraBits;
    }

    public byte[] getByteArray() {
        return this.byteArray;
    }

    @Override
    public void setHuffTree(String huffTree) {
        this.huffTree = huffTree;
    }

    @Override
    public void setExtraBits(int extraBits) {
        this.extraBits = extraBits;
    }

    @Override
    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
