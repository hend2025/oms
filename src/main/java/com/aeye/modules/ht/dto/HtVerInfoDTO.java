package com.aeye.modules.ht.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
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
public class HtVerInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	@NotNull(message="verId 不能为空")
	@ApiModelProperty(value = "版本ID-主键")
	private String verId;

	@ApiModelProperty(value = "所属产品")
	private String productId;

	@ApiModelProperty(value = "所属项目")
	private String projectId;

	@ApiModelProperty(value = "主版本号")
	private String firstVerNo;

	@ApiModelProperty(value = "次版本号")
	private String secondVerNo;

	/**
	 * 更新内容
	 */
	@ApiModelProperty(value = "更新内容")
	private String verContent;

	/**
	 * 版本状态,0规划中,1开发中,2测试中,3已发布
	 */
	@ApiModelProperty(value = "版本状态,1正在规划、2正在开发、3归档提测、4正在测试、5正式发布")
	private String verStatus;

	/**
	 * 发布时间
	 */
	@ApiModelProperty(value = "发布时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date releaseTime;

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
