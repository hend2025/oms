package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统应用
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_app")
public class HtAppDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 应用编号
	 */
	@TableId(type = IdType.INPUT)
	private String appCode;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 应用类型
	 */
	private String appType;

	/**
	 * 主版本号
	 */
	private String firstVerNo;

	/**
	 * 次版本号
	 */
	private String secondVerNo;

	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 数据创建时间
	 */
	private Date crteTime;

	/**
	 * 数据更新时间
	 */
	private Date updtTime;

    public static HtAppDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtAppDO.class);
    }

}
