<?php
include_once("db_conn.php");
//$id = $_GET['id'];
//$sqltest="select * from tb_headlineItem where headlineItemId='1' limit 1";  
$sqltest="insert into tb_date(date) values('$_POST[date]')";
$rst=mysql_query($sqltest); 
$ret = true;
if($rst){
	$ret=true;
}else{
	$ret=false;
}
echo json_encode($ret);
?>