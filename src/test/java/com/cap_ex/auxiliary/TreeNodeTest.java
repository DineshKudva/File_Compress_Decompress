package com.cap_ex.auxiliary;

import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import static org.junit.Assert.*;

public class TreeNodeTest {


    TreeNode testLeft = new TreeNode('#',1,null,null);
    TreeNode testRight = new TreeNode('#',1,null,null);

    TreeNode testChild = new TreeNode('x',2,null,null);
    TreeNode testNode = new TreeNode('a',2,testLeft,testRight);

    @Test
    public void testGetChar() {
        char actual = testNode.getChar();
        char expected = 'a';
        assertEquals(expected,actual);
    }

    @Test
    public void testGetFreq() {
        int actual = testNode.getFreq();
        int expected = 2;
        assertEquals(expected,actual);
    }

    @Test
    public void testGetAsciiVal(){
        int actual = testNode.getAsciiVal();
        int expected = (int)testNode.getChar();
        assertEquals(expected,actual);
    }

    @Test
    public void testGetLeft(){
        TreeNode actual = testNode.getLeftChild();
        TreeNode expected = testLeft;
        assertEquals(expected,actual);
    }

    @Test
    public void testGetRight(){
        TreeNode actual = testNode.getRightChild();
        TreeNode expected = testRight;
        assertEquals(expected,actual);
    }

    @Test
    public void testSetLeft(){
        testNode.setLeft(null);
        TreeNode actual = testNode.getLeftChild();
        TreeNode expected = null;
        assertEquals(expected,actual);

        testNode.setLeft(testChild);
        actual = testNode.getLeftChild();
        expected = testChild;
        assertEquals(expected,actual);
    }

    @Test
    public void testSetRight(){
        testNode.setRight(null);
        TreeNode actual = testNode.getRightChild();
        TreeNode expected = null;
        assertEquals(expected,actual);

        testNode.setRight(testChild);
        actual = testNode.getRightChild();
        expected = testChild;
        assertEquals(expected,actual);

    }

}