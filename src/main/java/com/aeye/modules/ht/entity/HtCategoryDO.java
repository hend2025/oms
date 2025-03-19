package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 分类
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_category")
public class HtCategoryDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类编码
     */
    @TableId(type = IdType.INPUT)
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 上级分类编码
     */
    private String parentCode;

    /**
     * 所属类别
     */
    private String blngType;

    /**
     * 排序
     */
    private Integer orderNum;

    public static HtCategoryDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtCategoryDO.class);
    }
}