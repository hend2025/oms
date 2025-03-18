package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ht_mapping")
public class HtMappingDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer mappingId;

	private String appCode;

	private String productId;

	private String projectId;

	private String verId;

	private Integer orderNum;

    public static HtMappingDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtMappingDO.class);
    }

}
