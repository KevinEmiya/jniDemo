package com.ky.jni;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ClassLoader clzLoader = Main.class.getClassLoader();
        System.load(clzLoader.getResource("libEasyAACEncoder.dll").getFile());
        System.load(clzLoader.getResource("jniDll.dll").getFile());

        File g726File = new File(clzLoader.getResource("encode_out_32.g726").getFile());
        String outputPath = g726File.getParentFile().getAbsolutePath() + "/audio_32.aac";

        byte[] g726Bytes = FileUtils.readFileToByteArray(new File(g726File.getAbsolutePath()));

        AacEncoderParam aacEncoderParam = new AacEncoderParam();
        aacEncoderParam.setRateBits(AacEncoderParam.G726_RATE_BITS_32K);
        byte[] aacBytes = AacEncoder.convert(g726Bytes, aacEncoderParam);
        FileUtils.writeByteArrayToFile(new File(outputPath), aacBytes);

    }

}
