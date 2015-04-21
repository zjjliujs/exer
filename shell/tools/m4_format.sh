#!/bin/bash
if [ $# -lt 1 ]
then
    echo "Usage: $( basename $0 ) <m4_file>"
    exit 1
fi

f=$1
t=$( mktemp )

#Remove the leading space in each line.
awk -e '{sub(/^[ \t]*/, "", $0); print $0}' $f > $t
#Remove line begin with =>
sed -i '/=>/d' $t
cp $t $f

#Delete the temp file
if [ $? -eq 0 ]
then
    rm -f $t
else
    echo "Error occured when copy $t $f"
    exit 2
fi
