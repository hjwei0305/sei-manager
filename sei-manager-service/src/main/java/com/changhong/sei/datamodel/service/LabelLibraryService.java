package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.dao.LabelLibraryDao;
import com.changhong.sei.datamodel.entity.LabelLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 标签库(LabelLibrary)业务逻辑实现类
 *
 * @author sei
 * @since 2020-07-28 17:31:48
 */
@Service("labelLibraryService")
public class LabelLibraryService extends BaseEntityService<LabelLibrary> {
    @Autowired
    private LabelLibraryDao dao;

    @Override
    protected BaseEntityDao<LabelLibrary> getDao() {
        return dao;
    }

}