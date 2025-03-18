package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ht_file")
public class HtFileDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer fileKey;

	private String fileName;

	private String fileType;

	private byte[] fileBlob;

	private Date crteTime;

	private Date updtTime;

    public static HtFileDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtFileDO.class);
    }

}
