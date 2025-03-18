package com.aeye.modules.ht.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("物料分类DTO")
public class HtMatterDTO {

    @ApiModelProperty("关键字")
    private String keyword;

    @ApiModelProperty("物料编码")
    private String matterCode;
    
    @ApiModelProperty("物料名称")
    private String matterName;
    
    @ApiModelProperty("上级ID")
    private String parentCode;
    
    @ApiModelProperty("节点类型：1节点、2叶子")
    private String matterType;
    
    @ApiModelProperty("物料参数")
    private String matterParam;
    
    @ApiModelProperty("排序")
    private Integer orderNum;
    
    @ApiModelProperty("备注")
    private String remarks;
}