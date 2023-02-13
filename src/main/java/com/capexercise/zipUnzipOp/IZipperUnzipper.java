package com.capexercise.zipUnzipOp;

public interface IZipperUnzipper {
    String compressFile(String inputFilePath);

    String decompressFile(String outputFilePath);
}
