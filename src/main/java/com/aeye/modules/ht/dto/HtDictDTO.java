package com.aeye.modules.ht.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典表
 * 如果同时想发布到rpc，请拷贝至公共依赖模块，用于rpc之间数据交互
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "数据字典表",description = "沈兴平-2024/09/27")
public class HtDictDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	/**
	 * 数据字典ID
	 */
	@NotNull(message="dicId 不能为空")
	@ApiModelProperty(value = "数据字典ID-主键")
	private String dicId;

	/**
	 * 字典值代码
	 */
	@ApiModelProperty(value = "字典值代码")
	private String dicCode;

	/**
	 * 字典值名称
	 */
	@ApiModelProperty(value = "字典值名称")
	private String dicName;

	/**
	 * 字典类型代码
	 */
	@ApiModelProperty(value = "字典类型代码")
	private String dicTypeCode;

	/**
	 * 字典类型名称
	 */
	@ApiModelProperty(value = "字典类型名称")
	private String dicTypeName;

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
