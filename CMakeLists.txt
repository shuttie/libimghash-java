project(libimghash-java)
cmake_minimum_required(VERSION 2.8)
aux_source_directory(./src/c SRC_LIST)
add_library(imghash-java SHARED imghash_wrapper.c)
target_link_libraries(imghash)

install(TARGETS imghash-java
    LIBRARY DESTINATION lib
)
