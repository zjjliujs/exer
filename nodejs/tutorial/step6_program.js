var step6mod = require ("./step6_module.js");

step6mod(process.argv[2], process.argv[3], function(err, list){
  if (err != null) {
    console.log(err);
    return;
  }
  list.forEach (function (f){
    console.log (f);
  })
})
