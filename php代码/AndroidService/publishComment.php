<?php
include_once("db_conn.php");

list($t1,$t2) = explode(' ',microtime());
$time=(floatval($t1)+floatval($t2))*1000;

if(empty($_POST['userId'])){
	$sqlstrii="insert into tb_pic_comment(picItemId,comment,userId,time) 
values('$_POST[picId]','$_POST[comment]',NULL,'$time')";
}else{
	$id = $_POST['userId'];
	$sqlstrii="insert into tb_pic_comment(picItemId,comment,userId,time) 
values('$_POST[picId]','$_POST[comment]','$id','$time')";
}

	

        if( mysql_query($sqlstrii)){
		echo "<script>alert('添加成功');history.go(-1);</script>";
	    }else{
		echo "<script>alert('添加失败');history.go(-1);</script>";
		}

?>