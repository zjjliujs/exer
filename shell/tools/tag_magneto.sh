#/bin/bash
find -name cscope.out -o -name cscope.po.out -o -name cscope.in.out -o -name tags |xargs rm -f
find . -wholename './ext' -prune -o -wholename './node_modules' -prune -o -type f -print |xargs cscope -b -q
find . -wholename './ext' -prune -o -wholename './node_modules' -prune -o -type f -print |xargs ctags
