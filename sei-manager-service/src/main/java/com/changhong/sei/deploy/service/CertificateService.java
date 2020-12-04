package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.deploy.dao.CertificateDao;
import com.changhong.sei.deploy.entity.Certificate;
import com.changhong.sei.deploy.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * 凭证(Certificate)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:33:54
 */
@Service("certificateService")
public class CertificateService extends BaseEntityService<Certificate> {
    @Autowired
    private CertificateDao dao;
    @Autowired
    private NodeService nodeService;

    @Override
    protected BaseEntityDao<Certificate> getDao() {
        return dao;
    }


    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查状态控制删除
        Certificate module = this.findOne(id);
        if (Objects.isNull(module)) {
            return OperateResult.operationFailure("[" + id + "]凭证不存在,删除失败!");
        }
        if (nodeService.isExistsByProperty(Node.FIELD_CERTIFICATE_ID, id)) {
            return OperateResult.operationFailure("[" + id + "]凭证已被应用于服务器节点上,不允许删除!");
        }
        return super.preDelete(id);
    }
}