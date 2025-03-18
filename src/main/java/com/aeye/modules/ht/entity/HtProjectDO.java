package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("ht_project")
public class HtProjectDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.INPUT)
	private String projectId;

	private String projectName;

	private String productId;

	@TableField(exist = false)
	private String appCode;

	private String firstVerNo;

	private String secondVerNo;

	private Integer orderNum;

	private String remarks;

	private Date crteTime;

	private Date updtTime;

    public static HtProjectDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtProjectDO.class);
    }

}
