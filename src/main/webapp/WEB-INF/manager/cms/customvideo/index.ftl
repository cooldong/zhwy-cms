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

	<@ms.modal  modalName="selDict" title="选择视频类型" >
		<@ms.modalBody>
			<#assign peopleSexs=[{"id":"1","name":"类型1"},{"id":"2","name":"类型2"}]>
			<@ms.radio name="selDictRadio" label="" list=peopleSexs listKey="id" listValue="name" />
			<@ms.modalButton>
            <!--模态框按钮组-->
				<@ms.button  value="确认"  id="selDictBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>

</@ms.html5>

<script>
	$(function(){
		$("#peopleUserList").bootstrapTable({
			url:"${managerPath}/custom/video/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
						{
							field: 'videoId',
							title: 'videoId',
							width:'10',
							align: 'center'
						},
				    	{
				        	field: 'name',
				        	title: '视频名称',
				        	width:'10',
				        	align: 'center'
				    	},	{
				        	field: 'type',
				        	title: '类型',
				        	width:'60',
				        	formatter:function(value,row,index) {
				        		return value === 1?"类型1":"类型2";
				        	}
				    	},	{
				        	field: 'url1',
				        	title: '视频1',
				        	width:'60'
				    	},	{
				        	field: 'des1',
				        	title: '描述1',
				        	width:'130'
				    	}	,	{
				        	field: 'url2',
				        	title: '视频2',
				        	width: '40',
				        	align: 'center'
				    	}	,	{
				        	field: 'des2',
				        	title: '描述2',
				        	width: '100',
				        	align: 'center'
				    	},	{
							title: '操作',
							width:'100',
							formatter:function(value,row,index) {
							    var s = "<button onclick='mesInfo(" + row.videoId + ")' class='btn btn-info'>查看留言</button>"
								return s;
							}
                }
					]
	    })
	})

    $("#selDictBtn").click(function(){
        location.href ="${managerPath}/custom/video/form.do?type="+$("input[type=radio][name=selDictRadio]:checked").val();
    })
	//增加按钮
	$("#addPeopleUserBtn").click(function(){
        $(".selDict").modal();
		<#--location.href ="${managerPath}/custom/video/form.do";-->
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
                url: "${managerPath}/custom/video/delVideoByIds.do",
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
    
    function mesInfo(videoId) {
		location.href = "${managerPath}/custom/video/mesindex.do?videoid="+videoId;
    }


</script>