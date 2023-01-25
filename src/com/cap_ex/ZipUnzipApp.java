package com.cap_ex;

import java.util.Scanner;

public class ZipUnzipApp {
    public static void main(String[] args){

        long start = System.nanoTime();

        String inputFilePath;
        String resultPath = null;
        String choice;

        Scanner inp = new Scanner(System.in);

            System.out.println("Enter Choice:\n1-Compress\n2-Decompress");
            choice = inp.nextLine();

            System.out.println("Enter name of file tobe compressed or decompressed:");
            inputFilePath = inp.nextLine();
            inputFilePath = "/C:/Users/Dinesh/Desktop/"+inputFilePath+".txt";

            IZipperUnzipper fileZipUnzip = new HuffmanZipperUnzipper();

            switch (choice){
                case "1" :
                    resultPath = fileZipUnzip.compressFile(inputFilePath);
                    break;
                case "2":
                    resultPath = fileZipUnzip.decompressFile(inputFilePath);
                    break;
                default:
                    System.out.println("Invalid choice! Try again....");
            }

        if(resultPath.isEmpty()) {
            System.out.println("File not found!!\nEnter valid file name");
            return;
        }
        System.out.println("Resultant file at :"+resultPath);

        long end = System.nanoTime();

        System.out.println("Time taken: "+(end-start));

    }
}
