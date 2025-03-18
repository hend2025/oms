package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_ver_jobs")
public class HtJobsDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 定时任务ID
	 */
	@TableId
	private Integer jobId;

	/**
	 * 所属版本ID
	 */
	private String verId;

	/**
	 * 所属产品
	 */
	private String productId;

	/**
	 * 所属项目
	 */
	private String projectId;

	/**
	 * 所属微服务
	 */
	private String serviceName;

	/**
	 * 任务名称
	 */
	private String jobName;

	/**
	 * 执行器描述
	 */
	private String jobDesc;

	/**
	 * 任务执行CRON
	 */
	private String jobCron;

	/**
	 * 执行器任务参数
	 */
	private String executorParam;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 备注
	 */
	private String remarks;

	@TableField(exist = false)
	private String verStatus;

	/**
	 * 数据创建时间
	 */
	private Date crteTime;

	/**
	 * 数据更新时间
	 */
	private Date updtTime;

    public static HtJobsDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtJobsDO.class);
    }
}
