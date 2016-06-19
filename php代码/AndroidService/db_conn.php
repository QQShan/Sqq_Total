<?php
$path='127.0.0.1';
$userName='root';
$password='123456';   
$dbname='sqq_total';

$con = mysql_connect($path,$userName,$password);
mysql_query("SET names 'utf8'");
mysql_select_db($dbname);

//$name="ddd";
//$sql = "insert into tb_meeting_depart(departmentName) values('$name')";
//mysql_query($sql);
?>