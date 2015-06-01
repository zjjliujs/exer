#!/bin/bash
#stop dbservice
(jcmd|grep mgdbservice|awk '{print $1}'|xargs kill -9) &> /dev/null

sleep 1

c=$( jcmd|grep mgdbservice|wc -l )
if [ $c -ge 1 ]
then
  echo "Can't stop mgdbservice"
  exit 1
fi

(nohup mgdbserver -start &) &> /dev/null

sleep 1

c=$( jcmd|grep mgdbservice|wc -l )
if [ $c -lt 1 ]
then
  echo "Can't start mgdbservice"
  exit 2
fi
