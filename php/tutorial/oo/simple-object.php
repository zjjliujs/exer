<?php 
class Site { 
  /* 成员变量 */ 
  var $url; 
  var $title; 
   
  /* 成员函数 */ 
  function setUrl($par){ 
     $this->url = $par; 
  } 
   
  function getUrl(){ 
     echo $this->url . PHP_EOL; 
  } 
   
  function setTitle($par){ 
     $this->title = $par; 
  } 
   
  function getTitle(){ 
     echo $this->title . PHP_EOL; 
  } 
} 

$php = new Site; 
$taobao = new Site; 
$google = new Site; 

// 调用成员函数，设置标题和URL 
$php->setTitle( "php中文网" ); 
$taobao->setTitle( "淘宝" ); 
$google->setTitle( "Google 搜索" ); 

$php->setUrl( 'www.php.cn' ); 
$taobao->setUrl( 'www.taobao.com' ); 
$google->setUrl( 'www.google.com' ); 

// 调用成员函数，获取标题和URL 
$php->getTitle(); 
$taobao->getTitle(); 
$google->getTitle(); 

$php->getUrl(); 
$taobao->getUrl(); 
$google->getUrl(); 
?>
