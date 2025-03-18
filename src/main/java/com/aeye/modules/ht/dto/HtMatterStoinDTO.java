package com.aeye.modules.ht.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("物料入库DTO")
public class HtMatterStoinDTO {

    @ApiModelProperty("入库单ID")
    private Long stoinId;
    
    @ApiModelProperty("物料编码")
    private String matterCode;
    
    @ApiModelProperty("物料名称")
    private String matterName;
    
    @ApiModelProperty("物料类型")
    private String matterType;
    
    @ApiModelProperty("物料参数")
    private String matterPara;
    
    @ApiModelProperty("入库日期")
    private Date stoinDate;

    @ApiModelProperty("开始日期")
    private Date startDate;

    @ApiModelProperty("结束日期")
    private Date endDate;
    
    @ApiModelProperty("入库数量")
    private Integer stoinCnt;
    
    @ApiModelProperty("单价")
    private BigDecimal price;
    
    @ApiModelProperty("金额")
    private BigDecimal money;
    
    @ApiModelProperty("供应商编码")
    private String companyCode;
    
    @ApiModelProperty("供应商名称")
    private String companyName;
}