package com.dushop.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dushop.common.entity.Setting;
import com.dushop.common.entity.SettingCategory;

/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.setting
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-13  20:23
 *@Description: TODO
 *@Version: 1.0
 */

@Service
public class SettingService {
    @Autowired private SettingRepository repo;
    public List<Setting> getGeneralSettings() {
        return repo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
    }

}