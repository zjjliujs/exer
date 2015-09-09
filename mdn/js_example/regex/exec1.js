var myRe = /d(b+)d/g;

var myArray = myRe.exec("cdbbdbsbz");
console.log(myArray);

myArray = myRe.exec("cdbbdbsbzdbbbd");
console.log(myArray);

myArray = myRe.exec("cdbbdbsbzdbbbdxxxxx");
console.log(myArray);
