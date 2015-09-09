//date format 2014-08-09 or 2014-08-09 12:23:04
function parseDate(v) {
  var sreg = /\b(\d{4})-(\d{1,2})-(\d{1,2})\b/g;
  var lreg = /\b(\d{4})-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})\b/g;

  var r;
  if (sreg.test(v)){
    r = sreg;
  }
  if (lreg.test(v)){
    r = lreg;
  }
  if (!r){
    return null;
  }

  r.lastIndex = 0;
  var result = r.exec (v);
  var y = parseInt(result[1]);
  var M = parseInt(result[2]);
  var d = parseInt(result[3]);
  if (M>12 || M<1){
    console.log ("ERROR: month should between 1-12");
    return null;
  }
  if (d>31 || d<1){
    console.log("ERROR: day should between 1-31");
    return null;
  }

  if (r === lreg){
    var H = parseInt (result[4]);
    if (H>23 || H<0){
      console.log("ERROR: Hour shoulde between 0-23");
      return null;
    }
    var m = parseInt (result[5]);
    if (m>59 || m<0){
      console.log("ERROR: minute should between 0-59");
      return null;
    }
    var s = parseInt (result[6]);
    if (s>59 || s<0){
      console.log("ERROR: second should between 0-59");
      return null;
    }
    return new Date (y,M-1,d,H,m,s);
  }
  return new Date (y,M-1,d);
}

var d1 = parseDate ("2014-8-9 12:12:12");
console.log (d1);

var d2 = parseDate ("2014-8-9");
console.log (d2);
