package com.changhong.sei.deploy.service;

import com.changhong.sei.BaseUnitTest;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.deploy.dao.FlowTypeNodeDao;
import com.changhong.sei.deploy.dto.FlowTypeNodeDto;
import com.changhong.sei.deploy.entity.FlowTypeNode;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-16 13:57
 */
public class FlowDefinitionServiceTest extends BaseUnitTest {
    @Autowired
    private FlowDefinitionService service;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void saveType() {
    }

    @Test
    public void findTypeByPage() {
    }

    @Test
    public void saveTypeNode() {
        String json = "{\"typeId\":\"1\",\"handleAccount\":\"cheng.yi\",\"code\":1,\"name\":\"项目经理审核\",\"remark\":\"test\",\"handleUserName\":\"易成\"}";
        FlowTypeNodeDto dto = JsonUtils.fromJson(json, FlowTypeNodeDto.class);
        FlowTypeNode node = modelMapper.map(dto, FlowTypeNode.class);
        ResultData<FlowTypeNode> resultData = service.saveTypeNode(node);
        System.out.println(resultData);
    }

    @Test
    public void deleteTypeNode() {
    }

    @Test
    public void getTypeNodeByTypeId() {
    }

    @Test
    public void getTypeVersionByTypeId() {
    }

    @Test
    public void getTypeNodeRecord() {
    }

    @Test
    public void publish() {
    }
}