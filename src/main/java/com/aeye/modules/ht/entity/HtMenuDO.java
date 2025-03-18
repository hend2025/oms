package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_ver_menu")
public class HtMenuDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId
	private Integer menuId;

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
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 父菜单名
	 */
	private String parentName;

	/**
	 * 菜单URL
	 */
	private String menuUrl;

	/**
	 * 前端标识
	 */
	private String menuKey;

	/**
	 * 类型 0：目录 1：菜单 2：按钮
	 */
	private String menuType;

	/**
	 * 菜单图标
	 */
	private String menuIcon;

	/**
	 * 排序
	 */
	private Integer orderNum;

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

    public static HtMenuDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtMenuDO.class);
    }
}
