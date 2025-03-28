package com.aeye.modules.ht.entity;

import com.aeye.common.utils.AeyeBeanUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("ht_org")
public class HtOrgDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long orgId;

    private String orgCode;

    private String orgName;

    private String conerName;

    private String conerTel;

    private String addr;

    public static HtOrgDO copyBean(Object source) throws Exception {
        return AeyeBeanUtils.copyBean(source, HtOrgDO.class);
    }
}