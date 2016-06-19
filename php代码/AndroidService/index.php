<?php
include_once("db_conn.php");
$id = $_GET['id'];
$sqltest="select userPassword from tb_meeting_user where userId=$id limit 1";  
$rst=mysql_query($sqltest); 
$row=mysql_fetch_array($rst);
echo json_encode($row['userPassword']);
//$sss=1;
//$varname = 'sss';
//$$varname =2;
//echo $varname.$sss;
//$out =`dir c:`; 
//echo '.$out';
?>