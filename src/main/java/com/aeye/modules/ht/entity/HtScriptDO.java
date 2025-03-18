package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ht_ver_script")
public class HtScriptDO implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer scriptId;

	private String verId;

	private String productId;

	private String projectId;

	private String langType;

	private String codeType;

	private String inputMode;

	private String scriptText;

	private String fileKey;

	private String fileName;

	private String author;

	private String remarks;

	@TableField(exist = false)
	private String verStatus;

	private Date crteTime;

	private Date updtTime;

    public static HtScriptDO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtScriptDO.class);
    }

}
