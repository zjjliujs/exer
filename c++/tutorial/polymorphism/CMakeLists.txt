cmake_minimum_required(VERSION 3.10)

# set the project name and version
project(polyphorphism VERSION 1.0)

SET(CMAKE_C_COMPILER "gcc")
SET(CMAKE_CXX_COMPILER "g++")

add_executable(polyphorphism main.cpp polygon.cpp rectangle.cpp triangle.cpp) 
