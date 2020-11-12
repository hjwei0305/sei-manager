package com.changhong.sei.manager.service;

import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.test.BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-12 11:13
 */
public class FeatureServiceTest extends BaseUnitTest {

    @Autowired
    private FeatureService service;

    @Test
    public void selectByRoleIdList() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
        OperateResult result = service.delete("1072806379330342912");
        System.out.println(result);
    }

    @Test
    public void findChildByFeatureId() {
    }
}