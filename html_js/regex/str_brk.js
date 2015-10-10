var r = /(\r\n)|\n/g;
var s = "line 1\nline 2\r\nline3\nline 4\r\nline 5";
var m = r.exec (s);
var pIdx = 0;
while (m) {
    console.log(s.substring(pIdx, m.index));
    //匹配\r\n ,+2
    //匹配\n, +1
    pIdx = m.index + m[0].length;
    m = r.exec(s);
}
//最后一行可能没有换行符
if (pIdx < s.length) {
    console.log(s.substring(pIdx, s.length));
}
