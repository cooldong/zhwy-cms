<@ms.html5>
	 <@ms.nav title="视频管理" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="customVideoForm" isvalidation=true>
    		<@ms.hidden name="videoId" value="${(customVideoEntity.videoId)?default('')}"/>
			<@ms.text label="视频名称" name="name" value="${(customVideoEntity.name)?default('')}"  width="240px;" placeholder="请输入视频名称" validation={"data-bv-stringlength":"true","maxlength":"50","data-bv-stringlength-message":"视频名称长度不能超过五十个字符长度!"}/>
			<#assign isMenu=[{"id":"1","name":"类型1"},{"id":"2","name":"类型2"}]>
			<@ms.select  value="${(customVideoEntity.type)?default('')}" name="type" style="width: 25%;" id="videoTypeSelect" list=isMenu  listKey="id" listValue="name" label="类型"  title="类型" />
			<@ms.formRow label="视频1" width="400">
				<@ms.uploadFile path="customvideo"  inputName="url1" size="1"  msg="建议上传5M以下的文件"  maxSize="100" callBack="" isRename="false"/>
			</@ms.formRow>
			<@ms.textarea name="des1"  label="描述1"   rows="4"  placeholder="请输入描述1" width="500" value="${(customVideoEntity.des1)?default('')}" validation={"data-bv-stringlength":"true","data-bv-stringlength-max":"60","data-bv-stringlength-message":"描述1不能超过60个字符"}/>

			<#if customVideoEntity.type == 2>
				<@ms.formRow label="视频2" width="400">
					<@ms.uploadFile path="customvideo"  inputName="url2" size="1"  msg="建议上传5M以下的文件"  maxSize="100" callBack="" isRename="false"/>
				</@ms.formRow>
				<@ms.textarea name="des2"  label="描述2"   rows="4"  placeholder="请输入描述2" width="500" value="${(customVideoEntity.des2)?default('')}" validation={"data-bv-stringlength":"true","data-bv-stringlength-max":"60","data-bv-stringlength-message":"描述2不能超过60个字符"}/>

			</#if>

		</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>

	var url = "${managerPath}/custom/video/addVideo.do";
	if($("input[name = 'videoId']").val() > 0){
		url = "${managerPath}/custom/video/updateVideo.do";
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
					location.href = "${managerPath}/custom/video/index.do";
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
