package com.aeye.modules.ht.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务
 * 如果同时想发布到rpc，请拷贝至公共依赖模块，用于rpc之间数据交互
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "定时任务",description = "沈兴平-2024/09/27")
public class HtJobsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	/**
	 * 定时任务ID
	 */
	@NotNull(message="jobId 不能为空")
	@ApiModelProperty(value = "定时任务ID-主键")
	private Integer jobId;

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
	 * 所属微服务
	 */
	@ApiModelProperty(value = "所属微服务")
	private String serviceName;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名称")
	private String jobName;

	/**
	 * 执行器描述
	 */
	@ApiModelProperty(value = "执行器描述")
	private String jobDesc;

	/**
	 * 任务执行CRON
	 */
	@ApiModelProperty(value = "任务执行CRON")
	private String jobCron;

	/**
	 * 执行器任务参数
	 */
	@ApiModelProperty(value = "执行器任务参数")
	private String executorParam;

	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
	private String author;

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
