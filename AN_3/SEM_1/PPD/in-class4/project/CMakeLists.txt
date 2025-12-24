cmake_minimum_required(VERSION 3.27)
project(project)

set(CMAKE_CXX_STANDARD 17)

add_executable(project main.cpp)

find_package(OpenMP REQUIRED)


target_link_libraries(project PUBLIC OpenMP::OpenMP_CXX)


set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fopenmp")
