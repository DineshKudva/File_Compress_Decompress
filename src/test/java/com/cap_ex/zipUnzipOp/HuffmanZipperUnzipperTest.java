package com.cap_ex.zipUnzipOp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanZipperUnzipperTest {

    String inputFilePath;
    String compressedFilePath;
    IZipperUnzipper testRef;
    @Before
    public void setup(){
        inputFilePath="src/textFiles/nonexistent.txt";
        compressedFilePath="src/textFiles/compressedFiles/nonexistent.txt";
        testRef = new HuffmanZipperUnzipper();
    }

    @Test
    public void testCompressFileForNonExistentFile(){
        String actual = testRef.compressFile(inputFilePath);
        String expected = "nan";
        assertEquals("check for non existent file",expected,actual);

    }

    @Test
    public void testCompressFileForEmptyFile(){

        inputFilePath = "src/textFiles/empty.txt";
        String actual = testRef.compressFile(inputFilePath);
        String expected = "empty";
        assertEquals(expected,actual);
    }

    @Test
    public void testCompressFile() {

        inputFilePath = "src/textFiles/largeTest2.txt";
        String actual = testRef.compressFile(inputFilePath);
        String expected = "src/textFiles/compressedFiles/compressed.txt";
        assertEquals(expected,actual);
    }
    @Test
    public void testDecompressFileforNonExistentFile(){
        String actual = testRef.decompressFile(compressedFilePath);
        String expected = "nan";
        assertEquals(expected,actual);

    }

    @Test
    public void testDecompressFileForEmptyFile(){
        compressedFilePath = "src/textFiles/compressedFiles/empty.txt";
        String actual = testRef.decompressFile(compressedFilePath);
        String expected = "empty";
        assertEquals(expected,actual);

    }


    @Test
    public void testDecompressFile() {
        compressedFilePath = "src/textFiles/compressedFiles/compressed.txt";
        String actual = testRef.decompressFile(compressedFilePath);
        String expected = "src/textFiles/decompressedFiles/decompressed.txt";
        assertEquals(expected,actual);
    }
}