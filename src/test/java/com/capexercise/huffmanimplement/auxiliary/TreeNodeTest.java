package com.capexercise.huffmanimplement.auxiliary;

import com.capexercise.huffmanimplement.auxiliary.TreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TreeNodeTest {


    TreeNode testLeft, testRight, testChild, testNode, actual, expected;

    @Before
    public void setup(){
        testLeft = new TreeNode('#',1,null,null);
        testRight = new TreeNode('#',1,null,null);
        testChild = new TreeNode('x',2,null,null);
        testNode = new TreeNode('a',2,testLeft,testRight);
    }

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
    public void testGetLeftForNull(){
        actual = testChild.getLeftChild();
        assertEquals(null,actual);
    }
    @Test
    public void testGetLeft(){
        actual = testNode.getLeftChild();
        expected = testLeft;
        assertEquals(expected,actual);
    }

    @Test
    public void testGetRightForNull(){
        actual = testChild.getRightChild();
        assertEquals(null,actual);
    }
    @Test
    public void testGetRight(){
        actual = testNode.getRightChild();
        expected = testRight;
        assertEquals(expected,actual);
    }

    @Test
    public void testSetLeftForNull(){
        testNode.setRight(null);
        actual = testNode.getRightChild();
        assertEquals(null,actual);

    }

    @Test
    public void testSetLeft(){
        testNode.setLeft(testChild);
        actual = testNode.getLeftChild();
        expected = testChild;
        assertEquals(expected,actual);
    }

    @Test
    public void testSetRightForNull(){
        testNode.setRight(null);
        actual = testNode.getRightChild();
        assertEquals(null,actual);

    }

    @Test
    public void testSetRight(){

        testNode.setRight(testChild);
        actual = testNode.getRightChild();
        expected = testChild;
        assertEquals(expected,actual);

    }

}