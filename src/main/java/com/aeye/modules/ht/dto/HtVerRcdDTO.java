package com.aeye.modules.ht.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 版本信息
 * 如果同时想发布到rpc，请拷贝至公共依赖模块，用于rpc之间数据交互
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "版本信息",description = "沈兴平-2024/09/27")
public class HtVerRcdDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	@ApiModelProperty(value = "主键ID")
	private String verRcdId;

	@ApiModelProperty(value = "版本ID")
	private String verId;

	@ApiModelProperty(value = "所属产品")
	private String productId;

	@ApiModelProperty(value = "所属项目")
	private String projectId;

	@ApiModelProperty(value = "主版本号")
	private String firstVerNo;

	@ApiModelProperty(value = "次版本号")
	private String secondVerNo;

	@ApiModelProperty(value = "更新内容")
	private String verContent;

	@ApiModelProperty(value = "备注")
	private String remarks;

	private String mainFlag;

	@ApiModelProperty(value = "发布类型：0临时归档、1正式发布")
	private String releaseType;

	@ApiModelProperty(value = "数据创建时间")
	private Date crteTime;

	@ApiModelProperty(value = "数据更新时间")
	private Date updtTime;

}
