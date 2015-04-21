#!/usr/bin/awk -f
    {
        s="";
        if (NF == 1)
            s = $1;
        else if(NF > 1) {
            for (i=1; i<=NF; ++i){
                if (i==1)
                    s = s  $i;
                else {
                    fc = toupper(substr($i,1,1));
                    oc = substr($i,2);
                    s = s  fc;
                    s = s  oc;
                }
            }
        }
        if (length(s) != 0)
            printf ("this.%s = data.%s\n", s, s);
    }
