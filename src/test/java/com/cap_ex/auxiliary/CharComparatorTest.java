package com.cap_ex.auxiliary;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharComparatorTest {
    CharComparator testRef = new CharComparator();
    TreeNode node1 = new TreeNode('a',2,null,null);
    TreeNode node2 = new TreeNode('b',3,null,null);
    TreeNode node3 = new TreeNode('c',3,null,null);
    @Test
    public void testCompare(){
        int actual = testRef.compare(node1,node2);
        int expected = -1;
        assertEquals(expected,actual);

        actual = testRef.compare(node2,node3);
        expected = -1;
        assertEquals(expected,actual);
    }
}