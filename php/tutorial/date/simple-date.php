<?php
echo "今天是 " . date("Y/m/d") . "<br>";
echo "今天是 " . date("Y.m.d") . "<br>";
echo "今天是 " . date("Y-m-d") . "<br>";
echo "今天是 " . date("l");

date_default_timezone_set("Asia/Shanghai");
echo "现在时间是 " . date("h:i:sa");
?>
