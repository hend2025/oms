package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ht_middleware")
public class HtMiddlewareDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer middlewareId;

	private String productId;

	private String projectId;

	private String verId;

	private String middlewareName;

	private String middlewareVerNo;

	private Integer orderNum;

    public static HtMiddlewareDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtMiddlewareDO.class);
    }

}
