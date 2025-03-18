package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典表
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_dict")
public class HtDictDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据字典ID
	 */
	@TableId(type = IdType.INPUT)
	private String dicId;

	/**
	 * 字典值代码
	 */
	private String dicCode;

	/**
	 * 字典值名称
	 */
	private String dicName;

	/**
	 * 字典类型代码
	 */
	private String dicTypeCode;

	/**
	 * 字典类型名称
	 */
	private String dicTypeName;

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

    public static HtDictDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtDictDO.class);
    }
}
