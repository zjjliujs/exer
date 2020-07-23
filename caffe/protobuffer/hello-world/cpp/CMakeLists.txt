cmake_minimum_required(VERSION 2.6)
project(writer)
add_executable(writer helloworld.pb.cc writer.cpp)

target_link_libraries(writer protobuf)


project(reader)
add_executable(reader helloworld.pb.cc reader.cpp)

target_link_libraries(reader protobuf)
