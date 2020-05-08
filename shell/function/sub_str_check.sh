#!/bin/bash
if [ ! $# -eq 2 ]
then
    echo "Usage $0 <str1> <str2>"
    exit 1
fi

str1=$1
str2=$2

if [[ $str1 =~ $str2 ]]
then
    echo $str1 contains $str2
else
    echo $str1 not contains $str2
fi
