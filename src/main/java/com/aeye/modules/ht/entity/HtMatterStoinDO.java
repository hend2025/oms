package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("ht_matter_stoin")
public class HtMatterStoinDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long stoinId;

    private Long categoryId;

    private String categoryName;

    private Date stoinDate;

    private Integer stoinCnt;

    private BigDecimal price;

    private BigDecimal money;

    private BigDecimal payMoney;

    private Long orgId;

    private String orgCode;

    private String orgName;

    public static HtMatterStoinDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtMatterStoinDO.class);
    }

}