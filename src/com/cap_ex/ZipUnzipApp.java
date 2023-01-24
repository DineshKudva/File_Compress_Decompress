package com.cap_ex;

public class ZipUnzipApp {
    public static void main(String[] args){
        String inputFilePath = "/C:/Users/Dinesh/Desktop/myTest.txt";
        String outputFilePath;
        String resultPath;

        IZipperUnzipper fileZipUnzip = new HuffmanZipperUnzipper();

        outputFilePath = fileZipUnzip.compressFile(inputFilePath);

        resultPath = fileZipUnzip.decompressFile(outputFilePath);

    }
}
