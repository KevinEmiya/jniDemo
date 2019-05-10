#include "include/com_ky_jni_AacEncoder.h"
#include "include/EasyAACEncoderAPI.h"
#include <malloc.h>
#include <string.h>

jbyteArray as_byte_array(JNIEnv* env, unsigned char* buf, unsigned int len);
unsigned char* as_unsigned_char_array(JNIEnv* env, jbyteArray array, int len);

JNIEXPORT jbyteArray JNICALL Java_com_ky_jni_AacEncoder_convertToAac
(JNIEnv* env, jclass clz, jbyteArray param_input, 
	jint audioCodec, jint audioChannel, jint audioSampleRate, 
	jint pcmBitSize, jint rateBits, jint bufferSize) {

	printf("Aac Encoder Init Params: \n - audioCodec: %d\n - audioChannel: %d\n - audioSampleRate: %d\n - pcmBitSize: %d\n - rateBits: %d\n",
		audioCodec, audioChannel, audioSampleRate, pcmBitSize, rateBits);

	printf("Buffer size is: %d\n", bufferSize);

	InitParam initParam;
	initParam.u32AudioSamplerate = audioSampleRate;
	initParam.ucAudioChannel = audioChannel;
	initParam.u32PCMBitSize = pcmBitSize;
	initParam.ucAudioCodec = audioCodec;
	initParam.g726param.ucRateBits = rateBits;

	EasyAACEncoder_Handle handle = Easy_AACEncoder_Init(initParam);

	int g726DataSize = env->GetArrayLength(param_input);
	unsigned char* g726Data = as_unsigned_char_array(env, param_input, g726DataSize);
	unsigned char* aacData = (unsigned char*)malloc(g726DataSize * 2 * sizeof(unsigned char));

	int bG726BufferSize = bufferSize;
	int bAACBufferSize = 4 * bG726BufferSize;//提供足够大的缓冲区
	unsigned char* pbG726Buffer = (unsigned char*)malloc(bG726BufferSize * sizeof(unsigned char));
	unsigned char* pbAACBuffer = (unsigned char*)malloc(bAACBufferSize * sizeof(unsigned char));
	unsigned int accDataSize = 0;

	int bytesLeft = g726DataSize;
	int bytesRead = 0;
	int bytesWritten = 0;
	bool isSucc = true;

	while (bytesLeft > 0)
	{
		// 读取原始数据到buffer中
		int readSize = bytesLeft > bG726BufferSize ? 500 : bytesLeft;
		memcpy(pbG726Buffer, g726Data + bytesRead, readSize * sizeof(unsigned char));

		printf("bytes left: %d", bytesLeft);

		bytesRead += readSize;
		bytesLeft -= readSize;

		unsigned int out_len = 0;
		int ret = Easy_AACEncoder_Encode(handle, pbG726Buffer, readSize, pbAACBuffer, &out_len);
		if (ret > 0)
		{
			memcpy(aacData + bytesWritten, pbAACBuffer, out_len * sizeof(unsigned char));
			printf("%s:[%d] pbAACBuffer(%d) len=%d \n", __FUNCTION__, __LINE__, bAACBufferSize, out_len);
			bytesWritten += out_len;
		}
		else if (ret < 0){
			printf("FAILED! ret=%d\n", ret);
			isSucc = false;
			break;
		}
	}

	Easy_AACEncoder_Release(handle);

	if (!isSucc) {
		bytesWritten = 0;
	}
	jbyteArray result = as_byte_array(env, aacData, bytesWritten);

	free(pbG726Buffer);
	free(pbAACBuffer);
	free(g726Data);
	free(aacData);

	return result;

}

jbyteArray as_byte_array(JNIEnv* env, unsigned char* buf, unsigned int len) {
	jbyteArray array = env->NewByteArray(len);
	env->SetByteArrayRegion(array, 0, len, reinterpret_cast<jbyte*>(buf));
	return array;
}

unsigned char* as_unsigned_char_array(JNIEnv* env, jbyteArray array, int len) {
	unsigned char* buf = new unsigned char[len];
	env->GetByteArrayRegion(array, 0, len, reinterpret_cast<jbyte*>(buf));
	return buf;
}