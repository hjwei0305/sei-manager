package com.changhong.sei.manager.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.service.bo.OperateResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-14 10:59
 */
public class MenuServiceTest extends BaseUnitTest {

    @Autowired
    private MenuService service;

    @Test
    public void move() {
        String nodeId = "DC2D6D3A-24BF-11EB-9585-0242C0A84603";
        String targetParentId = "CB8C7E13-2577-11EB-9AAA-0242C0A84603";
        OperateResult result = service.move(nodeId, targetParentId);
        System.out.println(result);
    }

    @Test
    public void move1() {
        String nodeId = "DC2D6D3A-24BF-11EB-9585-0242C0A84603";
        String targetParentId = "A7CB6478-24BF-11EB-9585-0242C0A84603";
        OperateResult result = service.move(nodeId, targetParentId);
        System.out.println(result);
    }
}