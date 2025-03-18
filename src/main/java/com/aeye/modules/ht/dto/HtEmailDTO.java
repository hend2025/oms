package com.aeye.modules.ht.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 邮箱地址
 * 如果同时想发布到rpc，请拷贝至公共依赖模块，用于rpc之间数据交互
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "邮箱地址",description = "沈兴平-2024/09/27")
public class HtEmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	/**
	 * 邮箱地址ID
	 */
	@NotNull(message="emailId 不能为空")
	@ApiModelProperty(value = "邮箱地址ID-主键")
	private Integer emailId;

	/**
	 * 邮箱地址
	 */
	@ApiModelProperty(value = "邮箱地址")
	private String emailUrl;

	/**
	 * 真实姓名
	 */
	@ApiModelProperty(value = "真实姓名")
	private String emailName;

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
