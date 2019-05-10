#include "include/com_ky_jni_NativeCalculator.h"

JNIEXPORT jint JNICALL Java_com_ky_jni_NativeCalculator_calc
(JNIEnv* env, jclass clz, jint param_a, jint param_b) {
	int a = param_a;
	int b = param_b;
	return a + b;
}