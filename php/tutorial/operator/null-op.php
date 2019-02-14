<?php
// 如果 $_GET['user'] 不存在返回 'nobody'，否则返回 $_GET['user'] 的值
$username = $_GET['user'] ?? 'nobody';
echo $username, PHP_EOL;
// 类似的三元运算符
$username = isset($_GET['user']) ? $_GET['user'] : 'nobody';
echo $username, PHP_EOL;
?>
