package com.aeye.modules.ht.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HtMappingDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer mappingId;

	private String appCode;

	private String productId;

	private String projectId;

	private String verId;

	private Integer orderNum;

}
