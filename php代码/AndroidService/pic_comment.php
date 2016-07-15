<?php
header('Content-Type:text/html; charset=utf-8');
class picCommentItem{
	public $id,$picId,$userId,$comment,$time;
	public function __construct($id,$picId,$userId,$comment,$time){
		$this->id = $id;
		$this->picId = $picId;
		$this->userId = $userId;
		$this->comment = $comment;
		$this->time = $time;
	}
}
include_once("db_conn.php");
$count = $_GET['count'];
$picId = $_GET['picId'];
if(!isset($_GET['id'])){
	$sqltest="select id,picItemId,userId,comment,time
 from tb_pic_comment where picItemId=$picId order by id desc limit $count";  
}else{
	$id = $_GET['id'];
	$sqltest="select id,picItemId,userId,comment,time
 from tb_pic_comment where id<$id and picItemId=$picId order by id desc limit $count";  
}

$rst=mysql_query($sqltest); 
$arr= array();
while($row=mysql_fetch_array($rst)){
	$ret = new picCommentItem($row['id'],$row['picItemId'],$row['userId'],$row['comment'],$row['time']);
	$arr[]= $ret;
}
echo json_encode($arr);
?>