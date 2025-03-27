package com.aeye.modules.ht.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class HtMatterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String keyword;

    private Long matterId;

    private String matterCode;

    private String matterName;

    private Long categoryId;

    private String categoryName;

    private String aliasName;

    private String pinyin;

    private String param;

    private Integer orderNum;

}