package com.aeye.modules.ht.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 物料DTO
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@ApiModel(value = "物料DTO")
public class HtMatterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "物料ID")
    private Integer matterId;

    @ApiModelProperty(value = "物料编码")
    private String matterCode;

    @ApiModelProperty(value = "物料名称")
    private String matterName;

    @ApiModelProperty(value = "分类编码")
    private String categoryCode;

    @ApiModelProperty(value = "物料参数")
    private String matterParam;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}