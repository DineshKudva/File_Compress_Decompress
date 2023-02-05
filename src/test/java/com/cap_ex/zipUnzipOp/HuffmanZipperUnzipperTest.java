package com.cap_ex.zipUnzipOp;

import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanZipperUnzipperTest {

    String inputFilePath="src/textFiles/nonexistent.txt";
    String compressedFilePath="src/textFiles/compressedFiles/nonexistent.txt";
    IZipperUnzipper testRef = new HuffmanZipperUnzipper();

    @Test
    public void testCompressFile() {
        String actual = testRef.compressFile(inputFilePath);
        String expected = "nan";
        assertEquals(expected,actual);

        inputFilePath = "src/textFiles/empty.txt";
        actual = testRef.compressFile(inputFilePath);
        expected = "empty";
        assertEquals(expected,actual);

        inputFilePath = "src/textFiles/largeTest2.txt";
        actual = testRef.compressFile(inputFilePath);
        expected = "src/textFiles/compressedFiles/compressed.txt";
        assertEquals(expected,actual);
    }

    @Test
    public void testDecompressFile() {

        String actual = testRef.decompressFile(compressedFilePath);
        String expected = "nan";
        assertEquals(expected,actual);

        compressedFilePath = "src/textFiles/compressedFiles/empty.txt";
        actual = testRef.decompressFile(compressedFilePath);
        expected = "empty";
        assertEquals(expected,actual);

        compressedFilePath = "src/textFiles/compressedFiles/compressed.txt";
        actual = testRef.decompressFile(compressedFilePath);
        expected = "src/textFiles/decompressedFiles/decompressed.txt";
        assertEquals(expected,actual);
    }
}