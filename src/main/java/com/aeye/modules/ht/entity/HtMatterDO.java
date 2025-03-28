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

    @TableId(type = IdType.AUTO)
    private Long matterId;

    private String matterName;

    private String matterCode;

    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;

    private String aliasName;

    private String pinyin;

    private String matterPara;

    public static HtMatterDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtMatterDO.class);
    }

}