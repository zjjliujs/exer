#!/bin/bash
if [ $# -lt 1 ]; then
  echo "Usage: find_class <class_name>"
  echo "to find the class in all jar files in current dirctory"
  exit 1
fi

if [ $# -eq 1 ]; then
  grepstr="$1"
else
  grepstr="\($1\)"
  for s in ${@:2}
  do
    n+='\|\('$s'\)'
  done
fi

grepstr+=$n

for j in $( find . -name '*.jar' )
do
  jar tf "$j" |grep ${grepstr} > /dev/null
  if [ $? -eq 0 ]; then
    echo "$j"
  fi
done
