package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("ht_matter")
public class HtMatterDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long matterId;

    private String matterCode;

    private String matterName;

    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;

    private String aliasName;

    private String pinyin;

    private String param;

    private Integer orderNum;

    public static HtMatterDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtMatterDO.class);
    }

}