name=( hello world how are you )

echo "array length:" ${#name}


echo "*************************************"
echo "demo of \"\${name[*]}\""
echo "*************************************"
for i in "${name[*]}"
do
	echo $i
	echo "====="
done
echo "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"

echo

echo "*************************************"
echo "demo of \"\${name[@]}\""
echo "*************************************"
for i in "${name[@]}"
do
	echo $i
	echo "====="
done
echo "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
