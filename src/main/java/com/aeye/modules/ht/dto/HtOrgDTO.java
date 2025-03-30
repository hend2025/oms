package com.aeye.modules.ht.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(value = "机构DTO")
public class HtOrgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键字")
    private String searchKey;

    @ApiModelProperty(value = "机构ID")
    private Long orgId;

    @ApiModelProperty(value = "机构编码")
    private String orgCode;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "机构类型")
    private String orgType;

    @ApiModelProperty(value = "联系人")
    private String conerName;

    @ApiModelProperty(value = "联系电话")
    private String conerTel;

    @ApiModelProperty(value = "机构地址")
    private String addr;

}