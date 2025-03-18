package com.aeye.modules.ht.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HtProjectDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "关键字")
	private String keyWord;

	private String projectId;

	private String projectName;

	private String productId;

	private String appCode;

	private String firstVerNo;

	private String secondVerNo;

	private Integer orderNum;

	private String remarks;

	private Date crteTime;

	private Date updtTime;

}
