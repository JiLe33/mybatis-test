package com.itheima.reggie.test;

import java.io.*;

public class A {
    public static void main(String[] args) {
        String inputFilename = "C:\\Users\\花见\\Desktop\\input.txt";
        String outputFilename = "C:\\Users\\花见\\Desktop\\output.txt";

        // 读取 input.txt 文件内容并写入到 output.txt 文件
        readFileAndWriteToFile(inputFilename, outputFilename);
    }

    // 从输入文件读取内容，并将内容写入到输出文件
    public static void readFileAndWriteToFile(String inputFilename, String outputFilename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine(); // 写入换行符
            }
            System.out.println("内容已成功从 '" + inputFilename + "' 读取并写入到 '" + outputFilename + "' 中。");
        } catch (IOException e) {
            System.err.println("读写文件时出现错误：" + e.getMessage());
        }
    }
}
