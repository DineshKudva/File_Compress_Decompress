package com.CapEx;

import java.util.*;

public class CharComparator implements Comparator<TreeNode> {

    @Override
    public int compare(TreeNode o1, TreeNode o2) {
        // TODO Auto-generated method stub


        if(o1.getFreq() == o2.getFreq()) {

            if((int)o1.getChar()>(int)o2.getChar())
                return 1;
            else
                return -1;
        }

        return o1.getFreq() - o2.getFreq();
    }

}
