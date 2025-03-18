package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 版本信息
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_ver_info")
public class HtVerInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 版本ID
	 */
	@TableId(type = IdType.INPUT)
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
	 * 主版本号
	 */
	private String firstVerNo;

	/**
	 * 次版本号
	 */
	private String secondVerNo;

	/**
	 * 更新内容
	 */
	private String verContent;

	/**
	 * 版本状态,1正在规划、2正在开发、3归档提测、4正在测试、5正式发布
	 */
	private String verStatus;

	/**
	 * 发布时间
	 */
	private Date releaseTime;

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

    public static HtVerInfoDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtVerInfoDO.class);
    }
}
