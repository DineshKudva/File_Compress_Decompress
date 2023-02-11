package com.cap_ex.auxiliary;

import java.util.Comparator;

public class CharComparator implements Comparator<TreeNode> {

    @Override
    public int compare(TreeNode o1, TreeNode o2) {

        if (o1.getFreq() == o2.getFreq()) {
            return o1.getAsciiVal() - o2.getAsciiVal();
        }

        return o1.getFreq() - o2.getFreq();
    }

}
