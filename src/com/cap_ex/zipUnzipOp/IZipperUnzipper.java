package com.cap_ex.zipUnzipOp;

public interface IZipperUnzipper {
    String compressFile(String inputFilePath);

    String decompressFile(String outputFilePath);
}
