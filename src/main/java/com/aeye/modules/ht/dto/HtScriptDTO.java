package com.aeye.modules.ht.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "脚本",description = "沈兴平-2024/09/27")
public class HtScriptDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	@ApiModelProperty(value = "脚本ID-主键")
	private Integer scriptId;

	@ApiModelProperty(value = "所属版本ID")
	private String verId;

	@ApiModelProperty(value = "所属产品")
	private String productId;

	@ApiModelProperty(value = "所属项目")
	private String projectId;

	@ApiModelProperty(value = "语言类型")
	private String langType;

	@ApiModelProperty(value = "代码类型")
	private String codeType;

	@ApiModelProperty(value = "录入方式")
	private String inputMode;

	@ApiModelProperty(value = "脚本内容")
	private String scriptText;

	@ApiModelProperty(value = "文件KEY")
	private String fileKey;

	@ApiModelProperty(value = "文件名")
	private String fileName;

	@ApiModelProperty(value = "作者")
	private String author;

	@ApiModelProperty(value = "备注")
	private String remarks;

	@ApiModelProperty(value = "版本状态,1正在规划、2正在开发、3归档提测、4正在测试、5正式发布")
	private String verStatus;

	@ApiModelProperty(value = "数据创建时间")
	private Date crteTime;

	@ApiModelProperty(value = "数据更新时间")
	private Date updtTime;

}
