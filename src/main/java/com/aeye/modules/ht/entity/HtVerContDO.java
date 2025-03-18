package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 版本更新说明
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_ver_cont")
public class HtVerContDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 版本内容ID
	 */
	@TableId
	private Integer verContId;

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
	 * 所属系统(模块)
	 */
	private String moduleName;

	/**
	 * 更新内容
	 */
	private String changeText;

	/**
	 * 是否新增功能
	 */
	private String newFlag;

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

    public static HtVerContDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtVerContDO.class);
    }
}
