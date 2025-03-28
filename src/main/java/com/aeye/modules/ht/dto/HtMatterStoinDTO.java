package com.aeye.modules.ht.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class HtMatterStoinDTO {

    private String keyword;

    private Long stoinId;
    
    private String matterId;

    private String matterCode;

    private String matterName;

    private Long categoryId;

    private String categoryName;
    
    private String matterPara;
    
    private Date stoinDate;

    private Date startDate;

    private Date endDate;
    
    private Integer stoinCnt;
    
    private BigDecimal price;
    
    private BigDecimal money;
    
    private String orgCode;
    
    private String orgName;

}