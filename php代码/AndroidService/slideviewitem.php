<?php
header('Content-Type:text/html; charset=utf-8');
class slideviewItem{
	public $title,$time,$picUrl,$Url,$id;
	public function __construct($id,$Title,$Time,$PicUrl,$Url){
		$this->id = $id;
		$this->title = $Title;
		$this->time = $Time;
		$this->picUrl = $PicUrl;
		$this->Url = $Url;
	}
}
include_once("db_conn.php");

$count = $_GET['count'];
if(isset($_GET['id'])){
	$id = $_GET['id'];
	$sqltest="select slideviewItemId,slideviewItemTitle,slideviewItemTime,slideviewItemPicUrl,slideviewItemUrl
 from tb_slideviewitem where slideviewItemId<$id order by slideviewItemId desc limit $count";  
}else{
	$sqltest="select slideviewItemId,slideviewItemTitle,slideviewItemTime,slideviewItemPicUrl,slideviewItemUrl
 from tb_slideviewitem order by slideviewItemId desc limit $count";  
}

$rst=mysql_query($sqltest); 
$arr= array();
while($row=mysql_fetch_array($rst)){
	$ret = new slideviewItem($row['slideviewItemId'],$row['slideviewItemTitle'],$row['slideviewItemTime'],$row['slideviewItemPicUrl'],$row['slideviewItemUrl']);
	$arr[]= $ret;
}
echo json_encode($arr);
?>