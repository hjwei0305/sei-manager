package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.datamodel.dao.DataModelTypeDao;
import com.changhong.sei.datamodel.entity.DataModelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * 数据模型分类(DataModelType)业务逻辑实现类
 *
 * @author sei
 * @since 2020-07-28 17:31:44
 */
@Service("dataModelTypeService")
public class DataModelTypeService extends BaseTreeService<DataModelType> {
    @Autowired
    private DataModelTypeDao dao;

    @Override
    protected BaseTreeDao<DataModelType> getDao() {
        return dao;
    }

    /**
     * 根据模型代码获取一个节点的树
     *
     * @param code 节点code
     * @return 节点树
     */
    public ResultData<DataModelType> getTreeByCode(String code) {
        DataModelType dataModelType = dao.findByProperty(DataModelType.CODE, code);
        if (Objects.nonNull(dataModelType)) {
            DataModelType tree = dao.getTree(dataModelType.getId());
            return ResultData.success(tree);
        }
        return ResultData.fail("未找到[" + code + "]模型类型.");
    }
}