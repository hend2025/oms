package com.aeye.modules.ht.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class HtMiddlewareDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer middlewareId;

	private String productId;

	private String projectId;

	private String verId;

	private String middlewareName;

	private String middlewareVerNo;

	private Integer orderNum;

}
