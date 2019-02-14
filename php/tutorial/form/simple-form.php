<html>
<head>
<meta charset="utf-8">
<title>php中文网(php.cn)</title>
</head>
<body>

<form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">
名字: <input type="text" name="fname">
年龄: <input type="text" name="age">
<input type="submit" value="提交">
</form>

欢迎 <?php echo $_POST["fname"]??"user"; ?>!<br>
你的年龄是 <?php echo $_POST["age"]??"unknown"; ?>  岁。

</body>
</html>
