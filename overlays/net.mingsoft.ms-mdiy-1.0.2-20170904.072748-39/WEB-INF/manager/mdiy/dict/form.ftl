<@ms.html5>
	 <@ms.nav title="字典表编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="dictForm" isvalidation=true>
    		<@ms.hidden name="dictId" value="${dictEntity.dictId?default('')}"/>
			<#if dictEntity.dictType == "1">
				<@ms.text label="数据值" name="dictValue" value="${dictEntity.dictValue?default('')}"  width="240px;" placeholder="请输入数据值" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"数据值长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
			</#if>

			<#if (dictEntity.dictType == "2" || dictEntity.dictType == "11" || dictEntity.dictType == "12")>
				<@ms.formRow colSm="2" label="上传图片" width="400" >
					<@ms.uploadImg path="dict" inputName="dictValue" size="1" msg="提示:上传图片,支持jpg格式"  imgs="${dictEntity.dictValue?default('')}"  />
				</@ms.formRow>
			</#if>

    			<@ms.number label="排序" name="dictSort" value="${dictEntity.dictSort?default('')}" width="240px;" placeholder="请输入排序（升序）" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"排序（升序）长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>

			<#if (dictEntity.dictType == "2")>
				<@ms.text label="标签名" name="dictLabel" value="${dictEntity.dictLabel?default('')}"  width="240px;" placeholder="请输入标签名" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"标签名长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
			</#if>

			<#if (dictEntity.dictType == "11")>
				<@ms.formRow label="栏目" width="300">
					<@ms.inputTree
					treeId="columnTree1"
					json="${listColumn?default('')}"
					jsonId="categoryId"
					jsonPid="categoryCategoryId"
					jsonName="categoryTitle"
					name="unuse"
					rootName="顶级节点"
					required=false
					text="${modelName?default('请选择')}"
					value="${tColumnId?default('0')}"
					onclick="selColumn"
					parent=true
					/>
				</@ms.formRow>

				<@ms.formRow label="文章" width="300">
					<select class="form-control" id="asel" name="dictLabel">
						<option>请选择</option>
					</select>
				</@ms.formRow>



			</#if>

			<#if (dictEntity.dictType == "12")>
				<@ms.text label="外链地址" name="dictLabel" value="${dictEntity.dictLabel?default('')}"  width="240px;" placeholder="请输入外链地址" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"外链地址长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
			</#if>
				<div hidden>
					<@ms.text label="类型" name="dictType" value="${dictEntity.dictType?default('')}"  width="240px;" placeholder="请输入类型" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"类型长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
				</div>
    			<@ms.text label="描述" name="dictDescription" value="${dictEntity.dictDescription?default('')}"  width="240px;" placeholder="请输入描述" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"描述长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.textarea label="备注信息" name="dictRemarks" value="${dictEntity.dictRemarks?default('')}"  width="240px;" placeholder="请输入备注信息" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"备注信息长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	$(function () {
		var columnId = '${tColumnId?default('')}';
        var acticleId = '${dictEntity.dictLabel?default('')}';

		if(columnId !== '' && acticleId !== ''){
            $.ajax({
                url:"${managerPath}/custom/mcms/article/web/list.do",
                data:{"cid":columnId},
                type:"get",
                success:function (result) {
                    var s = "<option>请选择</option>";
                    $.each(result,function (index,value) {
                        if(value.articleID == acticleId){
                            s += "<option selected value='" + value.articleID + "'>" + value.basicTitle + "</option>"
                        }else {
                            s += "<option value='" + value.articleID + "'>" + value.basicTitle + "</option>"
                        }
                    });
                    $("#asel").html(s);
                }
            })
		}
    })

    function selColumn(event,treeId,treeNode) {
        $.ajax({
			url:"${managerPath}/custom/mcms/article/web/list.do",
			data:{"cid":treeNode.categoryId},
			type:"get",
			success:function (result) {
			    var s = "<option>请选择</option>";
				$.each(result,function (index,value) {
					s += "<option value='" + value.articleID + "'>" + value.basicTitle + "</option>"
                });
				$("#asel").html(s);
            }

		})
    }

	var url = "${managerPath}/custom/mdiy/dict/save.do";
	if($("input[name = 'dictId']").val() > 0){
		url = "${managerPath}/custom/mdiy/dict/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#dictForm").data("bootstrapValidator").validate();
			var isValid = $("#dictForm").data("bootstrapValidator").isValid();
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
			data:$("form[name = 'dictForm']").serialize(),
			url:url,
			success: function(status) {
				if(status != null) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/mdiy/dict/index.do";
				}
				else{
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
					setTimeout(function () {
                        location.href= "${managerPath}/mdiy/dict/index.do";
                    }, 500)
				}
			}
		})
	}	
</script>

