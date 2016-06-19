<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加头条信息</title>
</head>
<body>
<div class="add_m_info">
<table cellpadding="0" cellspacing="0" border="0">
<form  id="theForm" name="theForm" action="addheadlineitem_chk.php" method="post" onSubmit="return check_submit();" enctype="multipart/form-data">
 <tr><td colspan="3" height="32"><h1 align="center">添加头条信息</h1></td></tr>
 <tr>
   <td width="120" height="28"><div align="center">标题:</div></td>
   <td><input class="input2"  type="text" name="itemTitle" /></td>
   <td align="left" width="180" ><span class="sp1">*填写头条信息标题</span></td> 
 </tr>
 <tr>
   <td height="28"><div align="center">简短的描述:</div></td>
   <td><input class="input2" type="text" name="description" /></td>
   <td align="left" width="180"><span class="sp1">*填写简短的描述</span></td> 
 </tr>
 <tr>
   <td height="28"><div align="center">图片地址:</div></td>
   <td><input class="input2" type="text" name="picurl" /></td>
   <td align="left" width="180"><span class="sp1">*填写图片地址&nbsp;&nbsp;</span></td> 
 </tr>
 <tr>
   <td height="28" ><div align="center">跳转的地址:</div></td>
   <td><input class="input2" type="text" name="url" /></td>
   <td align="left" width="180"><span class="sp1">*填写跳转的地址</span></td> 
 </tr>
 
 <tr>
 <td height="12"colspan="3"></td>
 </tr>
 <tr>
 <td  height="30" colspan="2"><center><input class="add_mbtn1" type="submit" value="提交"/>&nbsp;&nbsp;&nbsp;&nbsp;<input class="add_mbtn2" type="reset" value="重置" /></center></td><td></td>
 </tr>
</form>
</table>
</div>
</body>
</html>
