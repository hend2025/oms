package com.aeye.modules.ht.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(value = "分类DTO")
public class HtCategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String searchKey;

    private Long categoryId;

    private String categoryName;

    private String categoryCode;

    private Long parentId;

    private String parentName;

    private String nodeType;

    private String busiType;

    private String aliasName;

    private String pinyin;

    private String param;

    private Integer childNum;

}