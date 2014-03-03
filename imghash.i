%module imghash
%{
#define SWIG_FILE_WITH_INIT
#include "imghash_c.h"
%}

%pragma(java) jniclasscode=%{
    static {
        try {
            NativeLoader loader = new NativeLoader();
            loader.loadLibrary("imghash-java");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load. \n" + e);
            System.exit(1);
        }
    }
%}

%apply (char *STRING, int LENGTH) { (char *buffer, int bufferSize) };

extern unsigned long long imghash_buffer(char* buffer, int bufferSize);
extern unsigned long long imghash_file(char* fileName);
extern char* imghash_file_hex(char* fileName);
