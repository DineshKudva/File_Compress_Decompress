package com.capexercise.huffmanimplement.auxiliary;

import java.io.File;

public interface IGeneralMethods {
    char getAscii(String curCode);

    String serialize(TreeNode root);

    String getBinaryFromChar(char ch);


    String getBinaryFromInt(int val);

    IFileContents extractContents(File fileObj);

    void getStats(String path1, String path2);

    String addCompressedContent(IFileContents fileContents);


}
