#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_konsulio_app_utils_AESCrypt_aesKey(JNIEnv *env, jobject /* this */) {
    std::string key = "passwordAESCrypt123";
    return env->NewStringUTF(key.c_str());
}
