<?php
include_once("db_conn.php");

$basepath = "/uploadpic/";
$base_path = "./uploadpic/"; //接收原图目录

$basesmallpath = "/uploadsmallpic/";
$base_smallpath = "./uploadsmallpic/"; //接收小图目录
$isSave = false;
if (!file_exists($base_path)) {
    mkdir($base_path);
}
if (!file_exists($base_smallpath)) {
    mkdir($base_smallpath);
}

if($_FILES["uploadfile"]["error"]>0){
	//错误时
	$ilenema = $base_path."sqq1";
	if (!file_exists($ilenema)) {
		mkdir($ilenema);
	}
}else{
	$tmp_name = $_FILES["uploadfile"]["tmp_name"];
	$name = $_FILES["uploadfile"]["name"];
    $uploadfile = $base_path.$name;
    $isSave = move_uploaded_file($tmp_name, $uploadfile);
	
	if($isSave){
		//上传成功，开始压缩尺寸 宽300为标杆
		list($width,$height)=getimagesize($uploadfile);
		$percent = 300/$width;
		$new_width = $width*$percent;
		$new_height = $height*$percent;
		$image_p = imagecreatetruecolor($new_width, $new_height);
		$image = imagecreatefromjpeg($uploadfile);
		imagecopyresampled($image_p, $image, 0, 0, 0, 0, $new_width, $new_height, $width, $height);
		imagejpeg($image_p, $base_smallpath.$name);
		
		list($t1,$t2) = explode(' ',microtime());
		$time=(floatval($t1)+floatval($t2))*1000;
		$str = 'http://'.$_SERVER["HTTP_HOST"].$_SERVER["REQUEST_URI"];
		$address = substr($str,0,strrpos($str,'/')).$basepath.$name;
		$smalladdress = substr($str,0,strrpos($str,'/')).$basesmallpath.$name;
		$sqlstrii="insert into tb_picitem(picItemTitle,picItemTime,picItemPicUrl,picItemSmallPicUrl) 
		values('$_POST[itemTitle]','$time','$address','$smalladdress')";
		mysql_query($sqlstrii);
	}
	
}

?>