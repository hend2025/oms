package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ht_category")
public class HtCategoryDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long categoryId;

    private String categoryName;

    private String categoryCode;

    private String categoryType;

    private Long parentId;

    private String aliasName;

    private String pinyin;

    private String param;

    @TableField(exist = false)
    private Integer childNum;

    public static HtCategoryDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtCategoryDO.class);
    }

}