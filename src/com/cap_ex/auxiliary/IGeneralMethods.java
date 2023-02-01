package com.cap_ex.auxiliary;

import java.util.Map;

public interface IGeneralMethods {
    public char getAscii(String curCode);

    public String serialize(TreeNode root);

    public String getBinaryFromChar(char ch);

    public int getArraySize(Map<Character,String> charMap, Map<Character,Integer> freqMap);

    public String getBinaryFromInt(int val);


}
