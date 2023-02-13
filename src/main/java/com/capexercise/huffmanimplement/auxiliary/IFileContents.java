package com.capexercise.huffmanimplement.auxiliary;

public interface IFileContents {
    String getHuffTree();

    int getExtraBits();

    byte[] getByteArray();

    void setHuffTree(String huffTree);

    void setExtraBits(int extraBits);

    void setByteArray(byte[] byteArray);

}
