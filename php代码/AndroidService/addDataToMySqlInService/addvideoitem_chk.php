<?php
include_once("../db_conn.php");
//$datetime = date('Y-m-d H:i:s');
//$time = strtotime($datetime);
list($t1,$t2) = explode(' ',microtime());
$time=(floatval($t1)+floatval($t2))*1000;

$sqlstrii="insert into tb_videoitem(videoItemDescription,videoItemTime,videoItemPicUrl,videoItemUrl,videoItemNumber) 
values('$_POST[description]','$time','$_POST[picurl]','$_POST[url]',0)";

echo $_POST['description'].$time.$_POST['picurl'].$_POST['url'];
        if( mysql_query($sqlstrii)){
		echo "<script>alert('添加成功');history.go(-1);</script>";
	    }else{
		echo "<script>alert('添加失败');history.go(-1);</script>";
		}

?>