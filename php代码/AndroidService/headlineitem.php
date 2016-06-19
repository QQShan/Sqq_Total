<?php
header('Content-Type:text/html; charset=utf-8');
class headlineItem{
	//public $headlineItemTitle,$headlineItemDescription,$headlineItemTime,$headlineItemPicUrl,$headlineItemUrl;
	public $title,$description,$time,$picUrl,$Url,$id;
	public function __construct($id,$headlineItemTitle,$headlineItemDescription,$headlineItemTime,$headlineItemPicUrl,$headlineItemUrl){
		//$this->headlineItemTitle = $headlineItemTitle;
		//$this->headlineItemDescription = $headlineItemDescription;
		//$this->headlineItemTime = $headlineItemTime;
		//$this->headlineItemPicUrl = $headlineItemPicUrl;
		//$this->headlineItemUrl = $headlineItemUrl;
		$this->id = $id;
		$this->title = $headlineItemTitle;
		$this->description = $headlineItemDescription;
		$this->time = $headlineItemTime;
		$this->picUrl = $headlineItemPicUrl;
		$this->Url = $headlineItemUrl;
	}
}
include_once("db_conn.php");
$count = $_GET['count'];
if(!isset($_GET['id'])){
	$sqltest="select headlineItemId,headlineItemTitle,headlineItemDescription,headlineItemTime,headlineItemPicUrl,headlineItemUrl
 from tb_headlineItem order by headlineItemId desc limit $count";  
}else{
	$id = $_GET['id'];
	$sqltest="select headlineItemId,headlineItemTitle,headlineItemDescription,headlineItemTime,headlineItemPicUrl,headlineItemUrl
 from tb_headlineItem where headlineItemId<$id order by headlineItemId desc limit $count";  
}
//$sqltest="select headlineItemTitle,headlineItemDescription,headlineItemTime,headlineItemPicUrl,headlineItemUrl
// from tb_headlineItem order by headlineItemId desc limit $count";  

$rst=mysql_query($sqltest); 
$arr= array();
while($row=mysql_fetch_array($rst)){
	$ret = new headlineItem($row['headlineItemId'],$row['headlineItemTitle'],$row['headlineItemDescription'],$row['headlineItemTime'],$row['headlineItemPicUrl'],$row['headlineItemUrl']);
	$arr[]= $ret;
}
echo json_encode($arr);
?>