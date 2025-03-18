package com.aeye.modules.ht.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表
 * 如果同时想发布到rpc，请拷贝至公共依赖模块，用于rpc之间数据交互
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "菜单表",description = "沈兴平-2024/09/27")
public class HtMenuDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	/**
	 * 菜单ID
	 */
	@NotNull(message="menuId 不能为空")
	@ApiModelProperty(value = "菜单ID-主键")
	private Integer menuId;

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
	 * 菜单名称
	 */
	@ApiModelProperty(value = "菜单名称")
	private String menuName;

	/**
	 * 父菜单名
	 */
	@ApiModelProperty(value = "父菜单名")
	private String parentName;

	/**
	 * 菜单URL
	 */
	@ApiModelProperty(value = "菜单URL")
	private String menuUrl;

	/**
	 * 前端标识
	 */
	@ApiModelProperty(value = "前端标识")
	private String menuKey;

	/**
	 * 类型 0：目录 1：菜单 2：按钮
	 */
	@ApiModelProperty(value = "类型 0：目录 1：菜单 2：按钮")
	private String menuType;

	/**
	 * 菜单图标
	 */
	@ApiModelProperty(value = "菜单图标")
	private String menuIcon;

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
