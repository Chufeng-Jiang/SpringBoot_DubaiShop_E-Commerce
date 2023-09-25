<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|Category -Edit</title>
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
              <div class="card-header"><h4>Edit Category</h4></div>
              <div class="card-body">
                <form action="add" id="category-add-form" method="post" class="row">
                  <input type="hidden" name="id" value="${goodsCategory.id}">
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Category icon</span>
                    <input type="file" id="select-file" style="display:none;" onchange="upload('show-picture-img','icon')">
                    <input type="text" readonly="readonly" class="form-control required" id="icon" name="icon" value="${goodsCategory.icon}" placeholder="Please upload category icon" tips="Please upload category icon" />
                  	<span class="input-group-btn">
                      <button class="btn btn-primary" id="add-pic-btn" type="button">Click to upload</button>
                    </span>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Category Name</span>
                    <input type="text" class="form-control required" id="name" name="name" value="${goodsCategory.name}" placeholder="请输入分类名称" tips="请填写用分类名称" />
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Category</span>
                    <select name="parent.id" class="form-control" id="category">
                    	<#if goodsCategorys??>
                    		<option value="">Please select the super category</option>
                    		<#list goodsCategorys as gc>
                    		<option value="${gc.id}" <#if goodsCategory.parent??><#if gc.id == goodsCategory.parent.id>selected</#if></#if>>${gc.name}</option>
                    		</#list>
                    	</#if>
                    </select>
                  </div>
                  <div class="input-group m-b-10">
                    <span class="input-group-addon">Category Sort</span>
                    <input type="number" class="form-control" id="sort" name="sort" value="${goodsCategory.sort}" />
                  </div>
                  <div class="form-group col-md-12">
                    <button type="button" class="btn btn-primary ajax-post" id="edit-form-submit-btn">Confirm</button>
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
	$("#edit-form-submit-btn").click(function(){
		if(!checkForm("category-add-form")){
			return;
		}
		var data = $("#category-add-form").serialize();
		$.ajax({
			url:'edit',
			type:'POST',
			data:data,
			dataType:'json',
			success:function(data){
				if(data.code == 0){
					showSuccessMsg('Category Edition Successful.',function(){
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