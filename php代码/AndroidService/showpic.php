<?php
include_once("db_conn.php");
$id = $_GET['id'];
$sqltest="select userName from tb_picInfo where userId='1' limit 1";  
$rst=mysql_query($sqltest); 
$row=mysql_fetch_array($rst);
echo json_encode('hello'),json_encode($row['userName']);
?>