<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<base href="<%=basePath%>"/>
		<script src="src/main/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="src/main/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="src/main/js/hoverifyBootnav.js"></script>
    	<script type="text/javascript" src="src/main/js/init.js"></script>
    	<script src="src/main/js/iziModal.min.js" type="text/javascript" charset="utf-8"></script>
    	<!-- 极验js -->
    	<script src="http://static.geetest.com/static/tools/gt.js"></script>
		<link rel="stylesheet" type="text/css" href="src/main/css/htmleaf-demo.css">
		<link rel="stylesheet" href="src/main/css/hoverifyBootnav.css">
		<link rel="stylesheet" type="text/css" href="src/main/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="src/main/css/demo.css"/>
		<link rel="stylesheet" type="text/css" href="src/main/css/iziModal.min.css"/>
		<link rel="stylesheet" type="text/css" href="src/main/css/normalize.css"/>
		
		<!-- <link rel="stylesheet" type="text/css" href="demo/style/index.css" /> -->
	    <link rel="stylesheet" type="text/css" href="src/main/css/overhang.css" />
	    <link rel="stylesheet" type="text/css" href="src/main/css/prism.css" />
		<script type="text/javascript" src="src/main/js/jquery-ui.js"></script>
		<script type="text/javascript" src="src/main/js/overhang.js"></script>
	    <script type="text/javascript" src="src/main/js/prism.js"></script>
	    <script type="text/javascript" src="src/main/js/index.js"></script>
	    
		<style type="text/css">
			*{
				padding: 0;
				margin: 0;
			}
			body{
				height: 1800px;
			}
			.header{
				background-image: url(src/main/img/hero_bg.jpg);
				width: 100%;
				height: 650px;
			}
			.zhezhao{
				position:absolute;
				left: 0;
				top: 0;
				right: 0;
				bottom: 0;
				z-index: 1;
				height: 650px;
				background: rgba(0,0,0,0.3);
				-webkit-pointer-events: none;
				-moz-pointer-events: none;
				-ms-pointer-events: none;
				-o-pointer-events: none;
				pointer-events: none;
			}
			
			/*èå*/
			.menu{
				width: 100%;
				height: 75px;
				background-color: #494A5F;
				position: fixed;
				z-index: 999;
				font-size: 20px;
				padding-left: 200px;
			}
			.header a{
				color: #477ACA;
			}
			.login{
				float: right;
   			    margin-top: -40px;
			}
			.nav-pills a:hover{
				color: white;
			}
			/*è¿åé¡¶é¨*/
			#to-top a{
				background-image: url(src/main/img/share.png);
				background-repeat-y: no-repeat;
				width: 30px;
				overflow: hidden;
				text-indent: -999px;
				height: 30px;
			}
			#to-top{
				position:fixed;
				margin-left: 95%;
				bottom: 30px;
				display: none;
			}
			.sina {background-position:0 0; position:absolute; bottom:32px;animation: all 0.2s ease-in-out;}
			.tencent { background-position:0 -30px; position:absolute; bottom:0;animation: all 0.2s ease-in-out;}
			.totop{background-position:0 -120px;position:absolute;bottom:64px;cursor:pointer;animation: all 0.2s ease-in-out;}
			.sina:hover{
				background-position:-30px 0;
			}
			.tencent:hover{
				background-position:-30px -30px;
			}
			.totop:hover{
				background-position:-30px -120px;
			}
			#sinaWb{
				position: fixed;
				width: 10%;
				bottom: 60px;
				left: 85%;
				display: none;
			}
		</style>
<script type="text/javascript">
		$(function(){
			$("#imgDown").click(function(){
				 $("body").animate({scrollTop:260}, 1000);
			});
			
			$(window).on('scroll', revealOnScroll);
			
			//显示菜单
			$(document).mousemove(function(e){
      			  	e = e || window.event;
      			 	x = e.clientX;
     			  	y = e.clientY;
     			  	if(y<75){
     			  		$(".menu").slideDown("slow");
     			  	}else{
     			  		$(".menu").slideUp("slow");
     			  	}
				})
			$(function(){
				
			});
			
			//返回顶部
			$(window).scroll(function(){
					if($(document).scrollTop()>120){
						$("#to-top").fadeIn("slow");
					}else{
						$("#to-top").fadeOut("slow");
					}
				});
				
				$(".totop").click(function(){
					$("body").animate({scrollTop:0},1000);
				});
			
			$(".sina").mouseover(function(){
				$("#sinaWb").fadeIn("slow");
			});
			$(".sina").mouseleave(function(){
				$("#sinaWb").fadeOut("slow");
			})
			
			//初始化模态框
			$("#modal-custom").iziModal({
		        overlayClose: false,
		        width: 600,
		        autoOpen: false,
		        overlayColor: 'rgba(0, 0, 0, 0.6)',
		        onOpened: function() {
		            console.log('onOpened');
		        },
		        onClosed: function() {
		            console.log('onClosed');  
		        }
		    });
			/*登陆*/
			$(".login").click(function(event){
				event.preventDefault();
	       		$('#modal-custom').iziModal('open');
			})
			/*提交*/
			$("#modal-custom").on('click', '.submit', function(event) {
	        event.preventDefault();

	        var fx = "wobble",  //wobble shake,颤动
	            $modal = $(this).closest('.iziModal');
	        if($("#lName").val()==""||$("#lPass").val()==""){
	        	 $modal.addClass(fx);
	        	 setTimeout(function(){
		                $modal.removeClass(fx);
		            }, 1500);
	        }
	        /* if( !$modal.hasClass(fx) ){
	            $modal.addClass(fx);
	            setTimeout(function(){
	                $modal.removeClass(fx);
	            }, 1500);
	      	  } */
	   		});
	   		//如果没有输入信息
	   		$("#modal-custom").on('click', 'header a', function(event) {
		        event.preventDefault();
		        var index = $(this).index();
		        $(this).addClass('active').siblings('a').removeClass('active');
		        $(this).parents("div").find("section").eq(index).removeClass('hide').siblings('section').addClass('hide');
	
		        if( $(this).index() === 0 ){
		            $("#modal-custom .iziModal-content .icon-close").css('background', '#ddd');
		        } else {
		            $("#modal-custom .iziModal-content .icon-close").attr('style', '');
		        }
		    });
		});
		
		function revealOnScroll(){
			var top=$(document).scrollTop();
		}
</script>
<script>
    var handlerPopup = function (captchaObj) {
        // 成功的回调
        captchaObj.onSuccess(function () {
            var validate = captchaObj.getValidate();
            $.ajax({
                url: "user/login", // 进行二次验证
                type: "post",
                dataType: "json",
                data: {
                    username: $('#lName').val(),
                    password: $('#lPass').val(),
                    geetest_challenge: validate.geetest_challenge,
                    geetest_validate: validate.geetest_validate,
                    geetest_seccode: validate.geetest_seccode
                },
                success: function (data) {
                    if (data && (data.success == true)) {
                        $(document.body).html('<h1>登录成功</h1>');
                    }else{
                    	$("body").overhang({
                    		  type: "error",
                    		  message: data.msg,
                    		  duration: 2,
                    		  upper: true
                    	});
                    }
                }
            });
        });
        $("#popup-submit").click(function () {
            if($("#lName").val()!=""&&$("#lPass").val()!=""){
            	captchaObj.show();
	        }
        });
        // 将验证码加到id为captcha的元素里
        captchaObj.appendTo("#popup-captcha");
        // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
    };
    // 验证开始需要向网站主后台获取id，challenge，success（是否启用failback）
    $.ajax({
        url: "captcha/startCaptcha?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            // 使用initGeetest接口
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                product: "popup", // 产品形式，包括：float，embed，popup。注意只对PC版验证码有效
                offline: !data.success // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
            }, handlerPopup);
        }
    });
</script>
	</head>
<body>
		<!--菜单-->
		<div class="header">
			<div class="container menu">
			     <div class="row">
			        <div class="separator col-xs-12">
			            <ul class="nav nav-pills">
			                <li role="presentation" class="active"><a href="">首页</a></li>
			                <li role="presentation"><a href="">我的日记</a></li>
			                <li role="presentation"><a href="">我的消息</a></li>
			                <li role="presentation"><a href="">每日新闻</a></li>
			                <li role="presentation"><a href="">每时每刻</a></li>
			                <li role="presentation"><a href="">友情链接</a></li>
			            </ul>
			            <span class="login"><a>登录</a></span>
			        </div>
			    </div>
			</div>
			<div class="zhezhao">
			
			</div>
		</div>
		<!--登录注册模态框-->
		 <div id="modal-custom" class="iziModal">
	        <button data-iziModal-close class="icon-close"></button>
	        <header>
	            <a href="" id="signin" class="active">登录</a>
	            <a href="" >注册</a>
	        </header>
	        <section>
	            <input type="text" id="lName" placeholder="用户名">
	            <input type="password" id="lPass" placeholder="密码">
	            <footer>
	                <button data-iziModal-close>退出</button>
	                <button class="submit" id="popup-submit">登录</button>    
	                <div id="popup-captcha"></div>        
	            </footer>
	        </section>
	        <section class="hide">
	            <input type="text" placeholder="用户名">
	            <input type="text" placeholder="邮箱">
	            <input type="password" placeholder="密码">
	            <label for="check"><input type="checkbox" name="checkbox" id="check" value="1"> 我同意 <u>条款</u>.</label>
	            <footer>
	                <button data-iziModal-close>退出</button>
	                <button class="submit">创建新账户</button>            
	            </footer>
	        </section>
	    </div>
		
		<!--回到顶部-->
		<img id="sinaWb" src="src/main/img/ewmWB.png"/>
		<div id="to-top">
			<a class="totop">返回顶部</a><br>
			<a class="sina">分享sina</a><br>
			<a class="tencent">分享腾讯</a>
		</div>
	</body>
</html>
