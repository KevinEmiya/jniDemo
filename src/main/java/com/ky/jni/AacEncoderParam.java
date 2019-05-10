package com.ky.jni;

public class AacEncoderParam {

    public static final int AUDIO_CODEC_U_LAW = 0;
    public static final int AUDIO_CODEC_A_LAW = 1;
    public static final int AUDIO_CODEC_16BIT_UNIFORM_PCM = 2;
    public static final int AUDIO_CODEC_G726 = 3;

    public static final int G726_RATE_BITS_16K = 2;
    public static final int G726_RATE_BITS_24K = 3;
    public static final int G726_RATE_BITS_32K = 4;
    public static final int G726_RATE_BITS_40K = 5;

    //int audioCodec, int audioChannel, int audioSampleRate, int pcmBitSize, int rateBits

    private int audioCodec = AUDIO_CODEC_G726;

    private int audioChannel = 1;

    private int audioSampleRate = 8000;

    private int pcmBitSize = 16;

    private int rateBits = G726_RATE_BITS_32K;

    public int getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(int audioCodec) {
        this.audioCodec = audioCodec;
    }

    public int getAudioChannel() {
        return audioChannel;
    }

    public void setAudioChannel(int audioChannel) {
        this.audioChannel = audioChannel;
    }

    public int getAudioSampleRate() {
        return audioSampleRate;
    }

    public void setAudioSampleRate(int audioSampleRate) {
        this.audioSampleRate = audioSampleRate;
    }

    public int getPcmBitSize() {
        return pcmBitSize;
    }

    public void setPcmBitSize(int pcmBitSize) {
        this.pcmBitSize = pcmBitSize;
    }

    public int getRateBits() {
        return rateBits;
    }

    public void setRateBits(int rateBits) {
        this.rateBits = rateBits;
    }
}
