<?php
//echo $_SERVER["DOCUMENT_ROOT"].$_SERVER["PHP_SELF"];
$str = 'http://'.$_SERVER["HTTP_HOST"].$_SERVER["REQUEST_URI"];
$ret = substr($str,0,strrpos($str,'/'));
echo $ret;
?>