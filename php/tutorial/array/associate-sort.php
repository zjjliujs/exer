<?php
$age=array("Peter"=>"35","Ben"=>"37","Joe"=>"43");
asort($age);
print_r($age);

arsort($age);
print_r($age);

ksort($age);
print_r($age);

krsort($age);
print_r($age);
?>
