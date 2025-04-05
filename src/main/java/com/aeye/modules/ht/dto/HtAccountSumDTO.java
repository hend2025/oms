package com.aeye.modules.ht.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class HtAccountSumDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orgId;

    private String orgName;

    private String orgCode;

    private String conerName;

    private String conerTel;

    private BigDecimal receivable;

    private BigDecimal received;

    private BigDecimal payable;

    private BigDecimal paid;

    private BigDecimal balance;

}