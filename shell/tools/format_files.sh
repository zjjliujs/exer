#/bin/bash
tmpf=~/tmp/tmp.file
function format_one() {
    if [ $# -ne 1 ]
    then
        return
    fi
    
    i=$1
    if [ -d $i ]
    then
        cd $i
        for j in *
        do
            format_one "$j"
        done
        cd ..
    fi

    if [ -f $i ]
    then
        echo "dos2unix $i..."
        dos2unix $i
        echo "Done"

        echo "Expanding $i..."
        expand -t 4 $i > ${tmpf}
        mv ${tmpf} $i
        echo "Done"

        if [ $(basename $i) == 'Main.java' ]
        then
            echo "Replace WinDBManager with DBManager"
            sed -i 's/WinDBManager/DBManager/' $i
            echo "Done"
        fi
    fi
}

for i in $*
do
    format_one "$i"
done
