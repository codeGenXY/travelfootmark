<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信墙</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resource/bootstrap/css/bootstrap.min.css">
<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
<!--[if lt IE 9]>
   <script src="${pageContext.request.contextPath }/resource/bootstrap/js/html5shiv.js"></script>
   <script src="${pageContext.request.contextPath }/resource/bootstrap/js/respond.min.js"></script>
<![endif]-->
<style type="text/css">
body{
     height:100%;
     width:100%;
     margin:0;
 }
.cover{
     width: 100%;
     height: 100%;
     position: fixed;
     z-index: -10;
     _position: absolute;
     _top: expression(eval(document.body.scrollTop));
     _left: expression(eval(document.body.scrollLeft));
}
 .cover img{
     width:100%;
     height:100%;
     border:0;
}
#header{
	height: 90px;
	margin: 0 auto;
	line-height: 90px;
	text-align: center;
	font-weight: bold;
	font-size: 30px;
	color:#FFFFFF;
}
#contentin{
	border:#FFFFF 2px solid;
	box-shadow: 0 0 5px rgba(81, 203, 238, 1);
	-webkit-box-shadow: 0 0 5px rgba(81, 203, 238, 1);
	-moz-box-shadow: 0 0 5px rgba(81, 203, 238, 1);
	border-radius: 5px;
	background-color: #FFFFFF;
	padding:3px; 
	margin-bottom: 10px;
}
.imglogo{
	width:90px;
	height:90px;
	background:url('${pageContext.request.contextPath}/resource/img/imgframe.png') no-repeat 0 0;
	padding:3px;
	float:left;
}
.coment{
	font-size:20px;
}
</style>
</head>
<body>
	<!-- 背景 -->
	<div class="cover">
		<img alt="" src="${pageContext.request.contextPath }/resource/img/background.jpg">
	</div>
	<!-- 下面一个div没有实际作用，解决IE中bug -->
	<div></div>
	<!-- 正文 -->
	<div id="container" class="container" style="padding:7px;">
		 <div class="row" >
			<div id="header" class="col-md-12" >
				<div>
					<img src="${pageContext.request.contextPath }/resource/img/weixin.png" width="60" height="48" style="vertical-align: middle;"/>
					微信墙
				</div>
			</div>
		 </div>
		 <div id="content" class="row" style="padding-top:10px;height:560px;overflow-y:hidden;">
			<div id="contentin" class="col-md-12">
				<div class="imglogo">
					<img  style="width: 84px;height: 84px;" src="${pageContext.request.contextPath }/resource/img/myphoto.png"/>
				</div>
				<div class="coment" style="margin-left:100px;">
					<div>
						<div style="float:left;font-weight: bold;color: #51BAC1;">张三</div>
						<div style="float:right;padding-right:20px;">2014-11-30 12:59:34</div>
					</div>
					<div style="color:#eb4141;font-size: 25px;font-weight: bold;padding-top:30px;padding-left:3px;">
						1212131312`121212121<br/>
						1212131341rerf252qtrfr<br/>
					</div>
				</div>
			</div>
			<div id="contentin" class="col-md-12">
				<div class="imglogo">
					<img  style="width: 84px;height: 84px;" src="${pageContext.request.contextPath }/resource/img/myphoto.png"/>
				</div>
				<div class="coment" style="margin-left:100px;">
					<div>
						<div style="float:left;font-weight: bold;color: #51BAC1;">张三</div>
						<div style="float:right;padding-right:20px;">2014-11-30 12:59:34</div>
					</div>
					<div style="color:#eb4141;font-size: 25px;font-weight: bold;padding-top:30px;padding-left:3px;">
						12121313<br/>
						12121313<br/>
					</div>
				</div>
			</div>
			<div id="contentin" class="col-md-12">
				<div class="imglogo">
					<img  style="width: 84px;height: 84px;" src="${pageContext.request.contextPath }/resource/img/myphoto.png"/>
				</div>
				<div class="coment" style="margin-left:100px;">
					<div>
						<div style="float:left;font-weight: bold;color: #51BAC1;">张三</div>
						<div style="float:right;padding-right:20px;">2014-11-30 12:59:34</div>
					</div>
					<div style="color:#eb4141;font-size: 25px;font-weight: bold;padding-top:30px;padding-left:3px;">
						12121313<br/>
						12121313<br/>
					</div>
				</div>
			</div>
			<div id="contentin" class="col-md-12">
				<div class="imglogo">
					<img  style="width: 84px;height: 84px;" src="${pageContext.request.contextPath }/resource/img/myphoto.png"/>
				</div>
				<div class="coment" style="margin-left:100px;">
					<div>
						<div style="float:left;font-weight: bold;color: #51BAC1;">张三</div>
						<div style="float:right;padding-right:20px;">2014-11-30 12:59:34</div>
					</div>
					<div style="color:#eb4141;font-size: 25px;font-weight: bold;padding-top:30px;padding-left:3px;">
						12121313<br/>
						12121313<br/>
						12121313<br/>
						12121313<br/>
						12121313<br/>
					</div>
				</div>
			</div>
			<div id="contentin" class="col-md-12">
				<div class="imglogo">
					<img  style="width: 84px;height: 84px;" src="${pageContext.request.contextPath }/resource/img/myphoto.png"/>
				</div>
				<div class="coment" style="margin-left:100px;">
					<div>
						<div style="float:left;font-weight: bold;color: #51BAC1;">张三</div>
						<div style="float:right;padding-right:20px;">2014-11-30 12:59:34</div>
					</div>
					<div style="color:#eb4141;font-size: 25px;font-weight: bold;padding-top:30px;padding-left:3px;">
						12121313<br/>
						12121313<br/>
						12121313<br/>
						12121313<br/>
						12121313<br/>
					</div>
				</div>
			</div>
		 </div>
	</div>
</body>
<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="${pageContext.request.contextPath }/resource/jquery/jquery-1.10.2.js"></script>
<!-- 包括所有已编译的插件 -->
<script src="${pageContext.request.contextPath }/resource/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#content").hover(function () {
		    $(this).css('overflow-y','auto');
		  },
		  function () {
		    $(this).css('overflow-y','hidden');
		  });
	});
</script>
</html>