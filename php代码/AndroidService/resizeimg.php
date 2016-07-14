<?php
// 这个文件
$filename = './uploadpic/3.jpg';
$percent = 0.5;

// 内容类型
//header('Content-Type: image/jpeg');

// 获取新的尺寸
list($width, $height) = getimagesize($filename);
$new_width = $width * $percent;
$new_height = $height * $percent;

// 重新取样
$image_p = imagecreatetruecolor($new_width, $new_height);
$image = imagecreatefromjpeg($filename);
imagecopyresampled($image_p, $image, 0, 0, 0, 0, $new_width, $new_height, $width, $height);

// 输出
imagejpeg($image_p, './uploadpic/44.jpg');
?>