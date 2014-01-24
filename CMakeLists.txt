project(libimghash-java)
cmake_minimum_required(VERSION 2.8)
find_package(JNI)
if (JNI_FOUND)
    message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")    
    message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif()
FIND_PACKAGE(ImageMagick COMPONENTS Magick++ REQUIRED)
include_directories("${ImageMagick_INCLUDE_DIRS}")
include_directories("${JNI_INCLUDE_DIRS}")
include_directories("/usr/include/imghash")
aux_source_directory(./src/c SRC_LIST)
add_definitions("-DMAGICKCORE_QUANTUM_DEPTH=8" "-DMAGICKCORE_HDRI_ENABLE=0")
add_library(imghash-java SHARED src/c/imghash_wrap.cxx)
target_link_libraries(imghash-java ${JNI_LIBRARIES} ${ImageMagick_LIBRARIES})

install(TARGETS imghash-java
    LIBRARY DESTINATION lib
)
