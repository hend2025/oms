package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 配置信息
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_ver_config")
public class HtConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 配置ID
	 */
	@TableId
	private Integer configId;

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
	 * 配置YAML
	 */
	private String yamlText;

	/**
	 * 配置PROPERTIES
	 */
	private String propText;

	/**
	 * 更新人
	 */
	private String operName;

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

    public static HtConfigDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtConfigDO.class);
    }
}
