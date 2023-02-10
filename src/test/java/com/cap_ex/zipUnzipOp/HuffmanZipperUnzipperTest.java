package com.cap_ex.zipUnzipOp;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

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

        inputFilePath = "src/textFiles/testFile.txt";
        String actual = testRef.compressFile(inputFilePath);
        String expected = "src/textFiles/compressedFiles/testCompressed.txt";

        boolean flag = true;


        try {

            BufferedReader br1 = new BufferedReader(new FileReader(actual));
            BufferedReader br2 = new BufferedReader(new FileReader(expected));

            String line1 = br1.readLine();
            String line2 = br2.readLine();

            while(line1 != null){
                if(!line1.equals(line2)){
                    flag = false;
                    break;
                }
                line1 = br1.readLine();
                line2 = br2.readLine();
            }

            if (line1!=null || line2!=null)
                flag = false;

            br2.close();
            br1.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        assertTrue(flag);
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
        boolean flag = true;


        try {

            BufferedReader br1 = new BufferedReader(new FileReader(actual));
            BufferedReader br2 = new BufferedReader(new FileReader(expected));

            String line1 = br1.readLine();
            String line2 = br2.readLine();

            while(line1 != null){
                if(!line1.equals(line2)){
                    flag = false;
                    break;
                }
                line1 = br1.readLine();
                line2 = br2.readLine();
            }

            if (line1!=null || line2!=null)
                flag = false;

            br2.close();
            br1.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        assertTrue(flag);
    }
}