<@ms.html5>
	 <@ms.nav title="comment管理" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="customVideoForm" isvalidation=true>
    		<@ms.hidden name="messageId" value="${(customVideoEntity.messageId)?default('')}"/>
    		<@ms.hidden name="videoid" value="${(customVideoEntity.videoid)?default('')}"/>
			<@ms.text label="comment" name="comment" value="${(customVideoEntity.comment)?default('')}"  width="240px;" placeholder="请输入comment" validation={"data-bv-stringlength":"true","maxlength":"50","data-bv-stringlength-message":"comment长度不能超过五十个字符长度!"}/>

		</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/custom/video/addVideoMes.do";
	if($("input[name = 'videoId']").val() > 0){
		url = "${managerPath}/custom/video/updateVideoMes.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#customVideoForm").data("bootstrapValidator").validate();
			var isValid = $("#customVideoForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:$("form[name = 'customVideoForm']").serialize(),
			url:url,
			success: function(status) {
				if(status) {
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/custom/video/mesindex.do?videoid="+${(customVideoEntity.videoid)?default('')};
				}
				else{
					alert("失败");
					$(".btn-success").text(btnWord);
					$(".btn-success").prop("disabled",false);
				}
			}
		})
	}	
</script>
