package com.aeye.modules.ht.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 分类DTO
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "分类DTO")
public class HtCategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关键字")
    private String keyword;

    @ApiModelProperty(value = "分类编码")
    private String categoryCode;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "上级分类编码")
    private String parentCode;

    @ApiModelProperty(value = "所属类别")
    private String blngType;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;
}