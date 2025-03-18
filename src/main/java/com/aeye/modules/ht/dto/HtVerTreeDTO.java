package com.aeye.modules.ht.dto;

import com.aeye.common.utils.AeyeBeanUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class HtVerTreeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String itemCode;

	private String itemName;

	private String parentCode;

	private Integer itemLevel;

	private List<HtVerTreeDTO> children = new ArrayList<>();

	public static HtVerTreeDTO copyBean(Object source) throws Exception{
        return AeyeBeanUtils.copyBean(source, HtVerTreeDTO.class);
    }

}
