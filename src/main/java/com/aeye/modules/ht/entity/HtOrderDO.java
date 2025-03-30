package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("ht_order")
public class HtOrderDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long orderId;

    private Long categoryId;

    private String categoryName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date orderDate;

    private Integer orderCnt;

    private BigDecimal price;

    private BigDecimal money;

    private BigDecimal payMoney;

    private Long orgId;

    private String orgName;

    private String orgCode;

    public static HtOrderDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtOrderDO.class);
    }
}