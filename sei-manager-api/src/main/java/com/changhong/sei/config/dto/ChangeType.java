package com.changhong.sei.config.dto;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-06 06:30
 */
public enum ChangeType {
    /**
     * 新增
     */
    @Remark("新增")
    CREATE,
    /**
     * 编辑
     */
    @Remark("编辑")
    MODIFY,
    /**
     * 删除
     */
    @Remark("删除")
    DELETE,
    /**
     * 一致
     */
    @Remark("一致")
    EQUALS
}
