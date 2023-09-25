<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|User-Edit</title>
<#include "../common/header.ftl"/>

</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <aside class="lyear-layout-sidebar">
      
      <!-- logo -->
      <div id="logo" class="sidebar-header">
        <a href="index.html"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}" alt="${siteName!""}" /></a>
      </div>
      <div class="lyear-layout-sidebar-scroll"> 
        <#include "../common/left-menu.ftl"/>
      </div>
      
    </aside>
    <!--End 左侧导航-->
    
    <#include "../common/header-menu.ftl"/>
    
     <!--页面主要内容-->
    <main class="lyear-layout-content">
      
      <div class="container-fluid">
        
        <div class="row">
          <div class="col-lg-12">
            <div class="card">
              <div class="card-header"><h4>Edit User</h4></div>
              <div class="card-body">
                <form action="add" id="user-add-form" method="post" class="row">
                  <input type="hidden" name="id" value="${user.id}">
                  <div class="form-group col-md-12">
                    <label>Upload Photo</label>
                    <div class="form-controls">
                      <ul class="list-inline clearfix lyear-uploads-pic">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <figure>
                            <#if user.headPic?length gt 0>
                    		<img src="/photo/view?filename=${user.headPic}" id="show-picture-img">
                    		<#else>
                    		<img src="/admin/images/default-head.jpg" id="show-picture-img" alt="Default Pohto">
                    		</#if>
                          </figure>
                        </li>
                        <input type="hidden" name="headPic" id="headPic" value="${user.headPic}">
                        <input type="file" id="select-file" style="display:none;" onchange="upload('show-picture-img','headPic')">
                        <li class="col-xs-4 col-sm-3 col-md-2">
                          <a class="pic-add" id="add-pic-btn" href="javascript:void(0)" title="Click to upload"></a>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Login Name</span>
                    <input type="text" class="form-control required" id="username" name="username" value="${user.username}" placeholder="请输入用户名" tips="请填写用户名" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Login Password</span>
                    <input type="password" class="form-control required" id="password" name="password" value="${user.password}" placeholder="请输入登录密码" tips="请填写登录密码" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Role Group</span>
                    <select name="role.id" class="form-control" id="role">
                    	<#list roles as role>
                    	<option value="${role.id}" <#if user.role.id == role.id>selected</#if> >${role.name}</option>
                    	</#list>
                    </select>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Mobile</span>
                    <input type="tel" class="form-control" id="mobile" name="mobile" value="${user.mobile}" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Email</span>
                    <input type="email" class="form-control" id="email" name="email" value="${user.email}" />
                  </div>
                  <div class="input-group" style="margin-top:15px;margin-bottom:15px;padding-left:25px;">
                    Gender:
                    <label class="lyear-radio radio-inline radio-primary" style="margin-left:30px;">
                    <input type="radio" name="sex" value="1" <#if user.sex == 1> checked </#if> >
                    <span>Male</span>
                    </label>
                    <label class="lyear-radio radio-inline radio-primary">
                    <input type="radio" name="sex" value="2" <#if user.sex == 2> checked </#if>>
                    <span>Female</span>
                    </label>
                    <label class="lyear-radio radio-inline radio-primary">
                    <input type="radio" name="sex" value="0" <#if user.sex == 0> checked </#if>>
                    <span>Unknow</span>
                    </label>
                  </label>
                  </div>
                  <div class="input-group" style="margin-top:15px;margin-bottom:15px;padding-left:25px;">
                    Status:
                    <label class="lyear-radio radio-inline radio-primary" style="margin-left:30px;">
                    <input type="radio" name="status" value="1" <#if user.status == 1> checked </#if>>
                    <span>Regular</span>
                    </label>
                    <label class="lyear-radio radio-inline radio-primary">
                    <input type="radio" name="status" value="0" <#if user.status == 0> checked </#if>>
                    <span>Freeze</span>
                  </label>
                  </div>
                  <div class="form-group col-md-12">
                    <button type="button" class="btn btn-primary ajax-post" id="add-form-submit-btn">Confirm</button>
                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">Back</button>
                  </div>
                </form>
       
              </div>
            </div>
          </div>
          
        </div>
        
      </div>
      
    </main>
    <!--End 页面主要内容-->
  </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//提交按钮监听事件
	$("#add-form-submit-btn").click(function(){
		if(!checkForm("user-add-form")){
			return;
		}
		var data = $("#user-add-form").serialize();
		$.ajax({
			url:'edit',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('User Edition Successful',function(){
						window.location.href = 'list';
					})
				}else{
					showErrorMsg(data.msg);
				}
			},
			error:function(data){
				alert('Network Error');
			}
		});
	});
	//监听上传图片按钮
	$("#add-pic-btn").click(function(){
		$("#select-file").click();
	});
});
</script>
</body>
</html>