<?php
class Site {
  /* 成员变量 */
  var $url;
  var $title;

  function __construct( $par1, $par2 ) {
    $this->url = $par1;
    $this->title = $par2;
  }
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

$php = new Site('www.php.cn', 'php中文网'); 
$taobao = new Site('www.taobao.com', '淘宝'); 
$google = new Site('www.google.com', 'Google 搜索'); 

// 调用成员函数，获取标题和URL 
$php->getTitle(); 
$taobao->getTitle(); 
$google->getTitle(); 

$php->getUrl(); 
$taobao->getUrl(); 
$google->getUrl();
?>
