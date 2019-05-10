package com.ky.jni;

public class AacEncoder {

//    typedef struct _initParam
//    {
//        unsigned char	ucAudioCodec;			// Law_uLaw  Law_ALaw Law_PCM16 Law_G726
//        unsigned char	ucAudioChannel;			//1
//        unsigned int	u32AudioSamplerate;		//8000
//        unsigned int	u32PCMBitSize;			//16
//        union
//        {
//            G711Param g711param;
//            G726Param g726param;
//        };
//
//    }
//
//    InitParam;

//    initParam.u32AudioSamplerate = 8000;
//    initParam.ucAudioChannel = 1;
//    initParam.u32PCMBitSize = 16;
//    initParam.ucAudioCodec = Law_G726;
//    initParam.g726param.ucRateBits = Rate24kBits;

//    ///* Audio Codec */
//    enum Law
//    {
//        Law_ULaw	=	0, 		/**< U law */
//        Law_ALaw	=	1, 		/**< A law */
//        Law_PCM16	=	2, 		/**< 16 bit uniform PCM values. 原始 pcm 数据 */
//        Law_G726	=	3		/**< G726 */
//    };
//
//    ///* Rate Bits */
//    enum Rate
//    {
//        Rate16kBits=2,	/**< 16k bits per second (2 bits per ADPCM sample) */
//        Rate24kBits=3,	/**< 24k bits per second (3 bits per ADPCM sample) */
//        Rate32kBits=4,	/**< 32k bits per second (4 bits per ADPCM sample) */
//        Rate40kBits=5	/**< 40k bits per second (5 bits per ADPCM sample) */
//    };

    // PCM bitsize: 16, 20, 24

    public static byte[] convert(byte[] inputBytes, AacEncoderParam initParams) throws IllegalArgumentException {

        if (initParams == null) {
            throw new IllegalArgumentException("初始化参数不可为空！");
        }

        return convertToAac(inputBytes, initParams.getAudioCodec(), initParams.getAudioChannel(),
                initParams.getAudioSampleRate(), initParams.getPcmBitSize(), initParams.getRateBits(), 1024 * 128);
    }

    /**
     * 音频转码为Aac格式（JNI调用）
     *
     * @param inputBytes      - 原始音频数据
     * @param audioCodec      - 音频编码，与EasyAACEncoder枚举定义保持一致: 0 - U law, 1 - A law, 2 - 16bit uniform PCM, 3 - G726
     * @param audioChannel    - 音频通道，默认填1
     * @param audioSampleRate - 音频采样率，默认8000
     * @param pcmBitSize      - pcm位大小， 默认16，是否支持20和24未测试
     * @param rateBits        - 码率，与EasyAACEncoder枚举定义保持一致: 2 - 16kBits, 3 - 24kBits, 4 - 32kBits, 5 - 40kBits
     * @param bufferSize      - 缓冲帧大小，由于底层库实现的限制，每次转码的数据不能超过1024 * 128字节，因此缓冲帧大小也不能超过
     * @return
     */
    private static native byte[] convertToAac(byte[] inputBytes, int audioCodec, int audioChannel, int audioSampleRate, int pcmBitSize, int rateBits, int bufferSize);

}
