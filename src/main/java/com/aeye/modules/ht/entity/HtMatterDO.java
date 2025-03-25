package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 物料
 * @author 沈兴平
 * @date 2024/09/27
 */
@Data
@TableName("ht_matter")
public class HtMatterDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物料ID
     */
    @TableId(type = IdType.INPUT)
    private Integer matterId;

    /**
     * 物料编码
     */
    private String matterCode;

    /**
     * 物料名称
     */
    private String matterName;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 物料参数
     */
    private String matterParam;

    /**
     * 排序
     */
    private Integer orderNum;

    public static HtMatterDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtMatterDO.class);
    }
}