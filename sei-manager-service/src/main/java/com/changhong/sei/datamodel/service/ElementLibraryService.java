package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.datamodel.dao.ElementLibraryDao;
import com.changhong.sei.datamodel.entity.ElementLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 元素库(ElementLibrary)业务逻辑实现类
 *
 * @author sei
 * @since 2020-07-28 17:31:47
 */
@Service("elementLibraryService")
public class ElementLibraryService extends BaseEntityService<ElementLibrary> {
    @Autowired
    private ElementLibraryDao dao;

    @Override
    protected BaseEntityDao<ElementLibrary> getDao() {
        return dao;
    }

}