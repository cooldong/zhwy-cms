<@ms.html5>
	<@ms.nav title="字典表管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
		<@ms.text label="类型" name="dictType" value=""  width="240px;" placeholder="请输入类型" />
		<@ms.searchFormButton>
			 <@ms.queryButton onclick="search()"/> 
		</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@ms.addButton id="addDictBtn"/>
					<@ms.delButton id="delDictBtn"/>
				</@ms.buttonGroup>
			</@ms.panelNav>
		</div>
		<table id="dictList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delDict" title="数据删除" >
		<@ms.modalBody>是否删除所选中的数据
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteDictBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>

	<@ms.modal  modalName="selDict" title="选择类型" >
		<@ms.modalBody>
			<#assign peopleSexs=[{"id":"1","name":"文本"},{"id":"2","name":"图片"},{"id":"11","name":"轮播图/内网"},{"id":"12","name":"轮播图/外网"}]>
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
		$("#dictList").bootstrapTable({
			url:"${managerPath}/mdiy/dict/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
	    				{
				        	field: 'dictValue',
				        	title: '数据值',
				        	align: 'center'
				    	},{
				        	field: 'dictLabel',
				        	title: '标签名',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/custom/mdiy/dict/form.do?dictType="+row.dictType+"&dictId="+row.dictId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},{
				        	field: 'dictSort',
				        	title: '排序'
				    	},{
				        	field: 'dictType',
				        	title: '类型',
							formatter:function(value,row,index) {
								if(value === '1'){
								    return '文本';
								}else if(value === '2'){
                                    return '图片';
                                }else if(value === '11'){
                                    return '轮播图/内网';
                                }else if(value === '12'){
                                    return '轮播图/外网';
                                }
                                return '';
							}
				    	},{
				        	field: 'dictDescription',
				        	title: '描述'
				    	},{
				        	field: 'dictRemarks',
				        	title: '备注信息'
				    	}]
	    })
	})
	//增加按钮
	$("#addDictBtn").click(function(){
        $(".selDict").modal();
	})
    $("#selDictBtn").click(function(){
		location.href ="${managerPath}/custom/mdiy/dict/form.do?dictType="+$("input[type=radio][name=selDictRadio]:checked").val();
    })
	//删除按钮
	$("#delDictBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#dictList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delDict").modal();
		}
	})
	
	$("#deleteDictBtn").click(function(){
		var rows = $("#dictList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/mdiy/dict/delete.do",
			data: JSON.stringify(rows),
			dataType: "json",
			contentType: "application/json",
			success:function(msg) {
				if(msg.result == true) {
					<@ms.notify msg= "删除成功" type= "success" />
				}else {
					<@ms.notify msg= "删除失败" type= "fail" />
				}
				location.reload();
			}
		})
	});
	//查询功能
	function search(){
		var search = $("form[name='searchForm']").serializeJSON();
        var params = $('#dictList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#dictList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>