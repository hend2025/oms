package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 物料分类
 * @author Trae
 * @date 2024/01/16
 */
@Data
@TableName("ht_matter")
public class HtMatterDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物料编码
     */
    @TableId(type = IdType.INPUT)
    private String matterCode;

    /**
     * 物料名称
     */
    private String matterName;

    /**
     * 上级ID
     */
    private String parentCode;

    /**
     * 节点类型：1节点、2叶子
     */
    private String matterType;

    /**
     * 物料参数
     */
    private String matterParam;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 备注
     */
    private String remarks;

    public static HtMatterDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtMatterDO.class);
    }
}