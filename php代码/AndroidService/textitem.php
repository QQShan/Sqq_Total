<?php
header('Content-Type:text/html; charset=utf-8');
class textItem{
	public $title,$description,$time,$picUrl,$Url,$id;
	public function __construct($id,$Title,$Description,$Time,$PicUrl,$Url){
		$this->id = $id;
		$this->title = $Title;
		$this->description = $Description;
		$this->time = $Time;
		$this->picUrl = $PicUrl;
		$this->Url = $Url;
	}
}
include_once("db_conn.php");
$count = $_GET['count'];
if(!isset($_GET['id'])){
	$sqltest="select textItemId,textItemTitle,textItemDescription,textItemTime,textItemPicUrl,textItemUrl
 from tb_textitem order by textItemId desc limit $count";  
}else{
	$id = $_GET['id'];
	$sqltest="select textItemId,textItemTitle,textItemDescription,textItemTime,textItemPicUrl,textItemUrl
 from tb_textitem where textItemId<$id order by textItemId desc limit $count";  
}

$rst=mysql_query($sqltest); 
$arr= array();
while($row=mysql_fetch_array($rst)){
	$ret = new textItem($row['textItemId'],$row['textItemTitle'],$row['textItemDescription'],$row['textItemTime'],$row['textItemPicUrl'],$row['textItemUrl']);
	$arr[]= $ret;
}
echo json_encode($arr);
?>