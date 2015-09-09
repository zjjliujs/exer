#!/bin/bash
#kill the ssh process logged from specified ip.
function usage () {
    echo "kill_term.sh <ip>"
    echo "kill the ssh process logged from specified ip."
    exit 1
}

function getTermNames () {
    f=$1
    n=$( who|grep "$f"|awk '{print $2}' )
    echo "$n"
} 

if [ $# -ne 1 ]
then
    usage
fi

ip=$1

tnames=$( getTermNames "$ip" )
echo ${tnames[1]}
