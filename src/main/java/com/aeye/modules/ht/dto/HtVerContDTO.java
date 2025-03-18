package com.aeye.modules.ht.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 版本更新说明
 * 如果同时想发布到rpc，请拷贝至公共依赖模块，用于rpc之间数据交互
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "版本更新说明",description = "沈兴平-2024/09/27")
public class HtVerContDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	/**
	 * 版本内容ID
	 */
	@NotNull(message="verContId 不能为空")
	@ApiModelProperty(value = "版本内容ID-主键")
	private Integer verContId;

	/**
	 * 所属版本ID
	 */
	@ApiModelProperty(value = "所属版本ID")
	private String verId;

	@ApiModelProperty(value = "所属产品")
	private String productId;

	@ApiModelProperty(value = "所属项目")
	private String projectId;

	/**
	 * 所属系统(模块)
	 */
	@ApiModelProperty(value = "所属系统(模块)")
	private String moduleName;

	/**
	 * 更新内容
	 */
	@ApiModelProperty(value = "更新内容")
	private String changeText;

	@ApiModelProperty(value = "是否新增功能")
	private String newFlag;

	@ApiModelProperty(value = "版本状态,1正在规划、2正在开发、3归档提测、4正在测试、5正式发布")
	private String verStatus;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remarks;

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
