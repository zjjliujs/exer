#!/bin/bash
declare -a filenames

fs=$( ls )

mapfile filenames <<EOF
$fs
EOF

for i in "${filenames[@]}"
do
	echo $i
done
