cmake_minimum_required(VERSION 3.27)
project(temacpp)

set(CMAKE_CXX_STANDARD 17)

add_executable(temacpp main.cpp
        Sequential.cpp
        Sequential.h
        HorizontalAllocatedThreads.cpp
        HorizontalAllocatedThreads.h
        VerticalAllocatedThreads.cpp
        VerticalAllocatedThreads.h)
