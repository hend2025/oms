package com.aeye.modules.ht.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class HtOrderDTO implements Serializable {

    private String searchKey;

    private Long orderId;

    private Long categoryId;

    private String categoryName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date orderDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    private Integer orderCnt;

    private BigDecimal price;

    private BigDecimal money;

    private BigDecimal payMoney;

    private Long orgId;

    private String orgCode;

    private String orgName;

}