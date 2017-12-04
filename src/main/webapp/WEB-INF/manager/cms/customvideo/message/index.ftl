<@ms.html5>
	<@ms.nav title="用户基础信息表管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
		<@ms.text label="账号"  name="peopleName"  title="请输入用户昵称"  placeholder="请输入用户账号" value=""   />			  
		<@ms.text label="昵称"  name="puNickname"  title="请输入用户昵称"  placeholder="请输入用户昵称" value=""   />			  
		<@ms.text label="真实姓名"   name="puRealName"  title="请输入真实姓名"  placeholder="请输入真实姓名" value=""   />			  
		<#assign status=[{"id":"-1","name":"全部"},{"id":"1","name":"男"},{"id":"2","name":"女"}]>
		<@ms.select label="性别" list=status listValue="name" listKey="id"    name="puSex" value="" />
		<#assign status=[{"id":"-1","name":"全部"},{"id":"0","name":"未审核"},{"id":"1","name":"已审核"}]>
		<@ms.select label="审核状态" list=status listValue="name" listKey="id"    name="peopleState" value="" />
		<@ms.date label="注册时间" name="peopleDateTimes"     value="" />
		<@ms.searchFormButton>
			<@ms.queryButton onclick="search()"/>								
		</@ms.searchFormButton>
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@ms.addButton id="addPeopleUserBtn"/>
					<@ms.delButton id="delPeopleUserBtn"/>
				</@ms.buttonGroup>
			</@ms.panelNav>
		</div>
		<table id="peopleUserList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delPeopleUser" title="用户数据删除" >
		<@ms.modalBody>删除此用户
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button class="btn btn-danger rightDelete" value="删除"  id="deletePeopleUserBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#peopleUserList").bootstrapTable({
			url:"${managerPath}/custom/video/meslist.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
            queryParams : function(params){
				var param = {
					pageNumber: params.pageNumber,
					pageSize: params.pageSize,
					sortOrder: params.sortOrder,
					sortName: params.sortName,
                    videoid:${(customVideoEntity.videoId)?default('')}
				};
				return param;
            },
	    	columns: [{ checkbox: true},
						{
							field: 'messageId',
							title: 'messageId',
							width:'10',
							align: 'center'
						},
				    	{
				        	field: 'comment',
				        	title: '留言',
				        	width:'10',
				        	align: 'center'
				    	},	{
				        	field: 'oknum',
				        	title: 'oknum',
				        	width:'60'
				    	}
					]
	    })
	})

	//增加按钮
	$("#addPeopleUserBtn").click(function(){
		location.href ="${managerPath}/custom/video/mesform.do?videoid="+${(customVideoEntity.videoId)?default('')};
	})
	//删除按钮
	$("#delPeopleUserBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#peopleUserList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delPeopleUser").modal();
		}
	})
	
	$("#deletePeopleUserBtn").click(function(){
        var rows = $("#peopleUserList").bootstrapTable("getSelections");
        var ids=getSelectIds();
        if(ids.length === 0){
            return
		}else {
            $(this).text("正在删除...");
            $(this).attr("disabled","true");
            $.ajax({
                type: "post",
                url: "${managerPath}/custom/video/delVideoMesByIds.do",
                data: JSON.stringify(rows),
                dataType: "json",
                contentType: "application/json",
                success:function(msg) {
                    if(msg === true) {
					<@ms.notify msg= "删除成功" type= "success" />
                    }else {
					<@ms.notify msg= "删除失败" type= "fail" />
                    }
                    location.reload();
                }
            })
		}

	});
	//查询功能
	function search(){
		var search = $("form[name='searchForm']").serializeJSON();
        var params = $('#peopleUserList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#peopleUserList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}

    function getSelectIds(){
        var data = $("#peopleUserList").bootstrapTable('getSelections');
        var ids =new Array();
        if(data.length>=1){
            for(var i=0;i<data.length;i++){
                ids.push(data[i].videoId);
            }
        }	return ids;
    }

</script>