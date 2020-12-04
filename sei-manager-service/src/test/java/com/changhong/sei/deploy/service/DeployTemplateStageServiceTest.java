package com.changhong.sei.deploy.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.service.bo.OperateResult;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-04 11:25
 */
public class DeployTemplateStageServiceTest extends BaseUnitTest {

    @Autowired
    private DeployTemplateStageService stageService;

    @Test
    public void insertRelations() {
        OperateResult result = stageService.insertRelations("854D0AEA-35DF-11EB-8AB2-0242C0A84603", Lists.newArrayList("2F153512-35DF-11EB-8AB2-0242C0A84603"));
        System.out.println(result);
    }
}