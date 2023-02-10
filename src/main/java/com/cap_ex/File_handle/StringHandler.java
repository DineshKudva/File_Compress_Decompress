package com.cap_ex.File_handle;

public class StringHandler implements IData_Handle {

    String content;

    public StringHandler(String source){
        content = source;
    }

    public StringHandler(byte[] byteArray){
        content = new String(byteArray);
    }

    @Override
    public String readContent() {
        return this.content;
    }
}
