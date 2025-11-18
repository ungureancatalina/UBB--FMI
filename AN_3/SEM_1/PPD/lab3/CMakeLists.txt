cmake_minimum_required(VERSION 3.27)
project(lab3)

set(CMAKE_CXX_STANDARD 17)

# Cauta MPI
find_package(MPI REQUIRED)
include_directories(${MPI_INCLUDE_PATH})

# Adauga toate sursele si headerele
add_executable(lab3
        main.cpp
        MPI_Variant0.cpp
        MPI_Variant0.h
        MPI_Variant1.cpp
        MPI_Variant1.h
        MPI_Variant2.cpp
        MPI_Variant2.h
        MPI_Variant3.cpp
        MPI_Variant3.h
        CompareFiles.cpp
        CompareFiles.h
        GenerateRandom.cpp
        GenerateRandom.h
        Logger.cpp
        Logger.h
)

target_link_libraries(lab3 ${MPI_LIBRARIES})
