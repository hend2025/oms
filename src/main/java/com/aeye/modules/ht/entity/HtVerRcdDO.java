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
@TableName("ht_ver_rcd")
public class HtVerRcdDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer verRcdId;

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

	private String releaseType;

	/**
	 * 备注
	 */
	private String remarks;

	private String mainFlag;

	/**
	 * 数据创建时间
	 */
	private Date crteTime;

	/**
	 * 数据更新时间
	 */
	private Date updtTime;

    public static HtVerRcdDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtVerRcdDO.class);
    }

}
