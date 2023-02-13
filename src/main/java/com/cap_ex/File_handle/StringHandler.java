package com.cap_ex.File_handle;

public class StringHandler implements IData_Handle {

    StringBuilder content;

    public StringHandler(String source) {
        content = new StringBuilder(source);
    }

    public StringHandler(byte[] byteArray) {
        content = new StringBuilder(new String(byteArray));
    }

    @Override
    public StringBuilder readContent() {
        return this.content;
    }
}
