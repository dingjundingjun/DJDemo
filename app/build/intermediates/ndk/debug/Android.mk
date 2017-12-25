LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := dump
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/home/dingjun/AndroidStudioProjects/Demo/app/src/main/jni/main.c \
	/home/dingjun/AndroidStudioProjects/Demo/app/src/main/jni/Android.mk \

LOCAL_C_INCLUDES += /home/dingjun/AndroidStudioProjects/Demo/app/src/main/jni
LOCAL_C_INCLUDES += /home/dingjun/AndroidStudioProjects/Demo/app/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
