package com.aeye.modules.ht.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 版本关系表
 * 如果同时想发布到rpc，请拷贝至公共依赖模块，用于rpc之间数据交互
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "版本关系表",description = "沈兴平-2024/09/27")
public class HtVerRelaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	/**
	 * 版本关系ID
	 */
	@NotNull(message="verRelaId 不能为空")
	@ApiModelProperty(value = "版本关系ID-主键")
	private Integer verRelaId;

	/**
	 * 所属版本ID
	 */
	@ApiModelProperty(value = "所属版本ID")
	private String verId;

	/**
	 * 应用编号
	 */
	@ApiModelProperty(value = "应用编号")
	private String appCode;

	@ApiModelProperty(value = "应用名称")
	private String appName;

	@ApiModelProperty(value = "应用类型")
	private String appType;

	@ApiModelProperty(value = "所属产品")
	private String productId;

	@ApiModelProperty(value = "所属项目")
	private String projectId;

	@ApiModelProperty(value = "主版本号")
	private String firstVerNo;

	@ApiModelProperty(value = "次版本号")
	private String secondVerNo;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer orderNum;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remarks;

	@ApiModelProperty(value = "版本状态,1正在规划、2正在开发、3归档提测、4正在测试、5正式发布")
	private String verStatus;

	/**
	 * 数据创建时间
	 */
	@ApiModelProperty(value = "数据创建时间")
	private Date crteTime;

	/**
	 * 数据更新时间
	 */
	@ApiModelProperty(value = "数据更新时间")
	private Date updtTime;

}
