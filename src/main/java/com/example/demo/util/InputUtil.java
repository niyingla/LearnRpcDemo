package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @program: demo
 * @description:
 * @author: xiaoye
 * @create: 2019-09-05 15:19
 **/
public class InputUtil {
    private static String ZIP_FILE = "C:\\Users\\10479\\Desktop\\zipFile.zip";

    private static String FILE = "C:\\Users\\10479\\Desktop\\中农网正式环境部署清单_20190306.xlsx";

    private static String SUFFIX_FILE = "我.xlsx";



    public static void main(String[] args) {
        File file = new File(FILE);
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            FileChannel fileChannel = new FileInputStream(FILE).getChannel();
            zipOut.putNextEntry(new ZipEntry(SUFFIX_FILE));
            fileChannel.transferTo(0, file.length(), writableByteChannel);
            fileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
