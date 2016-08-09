<?php
header('Content-Type:text/html; charset=utf-8');
class videoCommentItem{
	public $id,$videoId,$userId,$comment,$time;
	public function __construct($id,$videoId,$userId,$comment,$time){
		$this->id = $id;
		$this->videoId = $videoId;
		$this->userId = $userId;
		$this->comment = $comment;
		$this->time = $time;
	}
}
include_once("db_conn.php");
$count = $_GET['count'];
$videoId = $_GET['videoId'];
if(!isset($_GET['id'])){
	$sqltest="select id,videoItemId,userId,comment,time
 from tb_video_comment where videoItemId=$videoId order by id desc limit $count";  
}else{
	$id = $_GET['id'];
	$sqltest="select id,videoItemId,userId,comment,time
 from tb_video_comment where id<$id and videoItemId=$videoId order by id desc limit $count";  
}

$rst=mysql_query($sqltest); 
$arr= array();
while($row=mysql_fetch_array($rst)){
	$ret = new videoCommentItem($row['id'],$row['videoItemId'],$row['userId'],$row['comment'],$row['time']);
	$arr[]= $ret;
}
echo json_encode($arr);
?>