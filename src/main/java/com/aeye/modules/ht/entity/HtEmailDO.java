package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 邮箱地址
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_email")
public class HtEmailDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 邮箱地址ID
	 */
	@TableId
	private Integer emailId;

	/**
	 * 邮箱地址
	 */
	private String emailUrl;

	/**
	 * 真实姓名
	 */
	private String emailName;

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

    public static HtEmailDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtEmailDO.class);
    }
}
