<?php
header('Content-Type:text/html; charset=utf-8');
class picItem{
	public $title,$time,$picUrl,$id,$smallPicUrl,$userId,$scanTimes;
	public function __construct($id,$Title,$Time,$PicUrl,$smallPicUrl,$userId,$scanTimes){
		$this->id = $id;
		$this->title = $Title;
		$this->time = $Time;
		$this->picUrl = $PicUrl;
		$this->smallPicUrl = $smallPicUrl;
		$this->userId = $userId;
		$this->scanTimes = $scanTimes;
	}
}
include_once("db_conn.php");
$count = $_GET['count'];
if(!isset($_GET['id'])){
	$sqltest="select picItemId,picItemTitle,picItemTime,picItemPicUrl,picItemSmallPicUrl,userId,scanTimes
 from tb_picitem order by picItemId desc limit $count";  
}else{
	$id = $_GET['id'];
	$sqltest="select picItemId,picItemTitle,picItemTime,picItemPicUrl,picItemSmallPicUrl,userId,scanTimes
 from tb_picitem where picItemId<$id order by picItemId desc limit $count";  
}

$rst=mysql_query($sqltest); 
$arr= array();
while($row=mysql_fetch_array($rst)){
	$ret = new picItem($row['picItemId'],$row['picItemTitle'],$row['picItemTime'],$row['picItemPicUrl'],$row['picItemSmallPicUrl']
	,$row['userId'],$row['scanTimes']);
	$arr[]= $ret;
}
echo json_encode($arr);
?>