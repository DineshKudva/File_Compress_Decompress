package com.cap_ex.File_handle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler implements IData_Handle {

    File fileObj;

    public FileHandler(String source) {
        fileObj = new File(source);
    }

    @Override
    public StringBuilder readContent() {
        StringBuilder result = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(fileObj);
            int val = fileReader.read();

            while (val != -1) {
                char ch = (char) val;
                result.append(ch);
                val = fileReader.read();
            }

            fileReader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
