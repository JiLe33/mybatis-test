package com.itheima.reggie.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteStreamExample {
    public static void main(String[] args) {
        try {
            FileInputStream inputStream = new FileInputStream("C:\\Users\\花见\\Desktop\\input.txt");
            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\花见\\Desktop\\output.txt");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            // 关闭流
            inputStream.close();
            outputStream.close();
            System.out.println("数据已成功从 input.txt 复制到 output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
