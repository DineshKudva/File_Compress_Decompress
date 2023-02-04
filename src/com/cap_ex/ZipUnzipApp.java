package com.cap_ex;

import com.cap_ex.zipUnzipOp.*;

import java.util.Scanner;

public class ZipUnzipApp {
    public static void main(String[] args){



        String inputFilePath;
        String resultPath = null;
        String choice;

        Scanner inp = new Scanner(System.in);

            System.out.println("Enter Choice:\n1-Compress\n2-Decompress");
            choice = inp.nextLine();

            System.out.println("Enter name of file to be compressed or decompressed:");
            inputFilePath = inp.nextLine();
            inputFilePath = "src/textFiles/"+inputFilePath+".txt";

            IZipperUnzipper fileZipUnzip = new HuffmanZipperUnzipper();

        long start = System.currentTimeMillis();

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

        long end = System.currentTimeMillis();

        if(resultPath.equals("nan")) {
            System.out.println("File not found!!\nEnter valid file name");
            return;
        } else if (resultPath.equals("empty")) {
            System.out.println("File is empty!!");
            return;
        }

        System.out.println("Resultant file at :"+resultPath);


        System.out.println("Time taken: "+(end-start)+" ms");

    }
}
