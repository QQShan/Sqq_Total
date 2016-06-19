<?php
header('Content-Type:text/html; charset=utf-8');
class videoItem{
	public $description,$time,$picUrl,$Url,$id,$number;
	public function __construct($id,$Description,$Time,$PicUrl,$Url,$number){
		$this->id = $id;
		$this->description = $Description;
		$this->time = $Time;
		$this->picUrl = $PicUrl;
		$this->Url = $Url;
		$this->number = $number;
	}
}
include_once("db_conn.php");
$count = $_GET['count'];
if(!isset($_GET['id'])){
	$sqltest="select videoItemId,videoItemDescription,videoItemTime,videoItemPicUrl,videoItemUrl,videoItemNumber
 from tb_videoItem order by videoItemId desc limit $count";  
}else{
	$id = $_GET['id'];
	$sqltest="select videoItemId,videoItemDescription,videoItemTime,videoItemPicUrl,videoItemUrl,videoItemNumber
 from tb_videoItem where videoItemId<$id order by videoItemId desc limit $count";  
}

$rst=mysql_query($sqltest); 
$arr= array();
while($row=mysql_fetch_array($rst)){
	$ret = new videoItem($row['videoItemId'],$row['videoItemDescription'],$row['videoItemTime'],$row['videoItemPicUrl'],$row['videoItemUrl'],$row['videoItemNumber']);
	$arr[]= $ret;
}
echo json_encode($arr);
?>