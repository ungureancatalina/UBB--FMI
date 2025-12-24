cmake_minimum_required(VERSION 3.10)
project(MPI_Asincron)

set(CMAKE_CXX_STANDARD 17)

# Găsește pachetul MPI
find_package(MPI REQUIRED)

include_directories(${MPI_INCLUDE_PATH})

add_executable(MPI_Asincron main.cpp)

target_link_libraries(MPI_Asincron ${MPI_LIBRARIES})
