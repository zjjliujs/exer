console.log( __dirname );

console.log( __filename );

function printHello(){
   console.log( "Hello, World!");
}
// Now call above function after 2 seconds
setTimeout(printHello, 2000);

function printHello2(){
   console.log( "Hello, World2!");
}
// Now call above function after 2 seconds
var t = setTimeout(printHello2, 2000);

// Now clear the timer
clearTimeout(t);


function printHello3(){
   console.log( "Hello, World3!");
}
// Now call above function after 2 seconds
setInterval(printHello3, 2000);
