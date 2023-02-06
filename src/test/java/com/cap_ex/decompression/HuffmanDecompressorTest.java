package com.cap_ex.decompression;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class HuffmanDecompressorTest {
    IHuffmanDecompress testRef = new HuffmanDecompressor();
    File fileObj = new File("src/textFiles/compressedFiles/nonExistent.txt");
    @Test(expected = RuntimeException.class)
    public void testDecompressFail() {
        testRef.decompress(fileObj);
    }

    @Test(expected = RuntimeException.class)
    public void testDecompressEmpty() {
        fileObj = new File("src/textFiles/compressedFiles/empty.txt");
        testRef.decompress(fileObj);
    }

    @Test
    public void testDecompress(){
        fileObj = new File("src/textFiles/compressedFiles/nonExistent.txt");
        String actual = testRef.decompress(fileObj);
        String expected = "src/textFiles/decompressedFiles/decompressed.txt";

        assertEquals(expected,actual);
    }

    @Test
    public void dataDecompression() {

    }

    @Test
    public void deserialize() {
    }

    @Test
    public void treeBuilder() {
    }
}