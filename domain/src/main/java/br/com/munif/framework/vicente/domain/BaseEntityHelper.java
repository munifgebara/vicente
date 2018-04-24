/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.domain;

import br.com.munif.framework.vicente.core.RightsHelper;
import br.com.munif.framework.vicente.core.UIDHelper;
import br.com.munif.framework.vicente.core.VicThreadScope;
import java.util.Date;

/**
 *
 * @author munif
 */
public class BaseEntityHelper {

    public static BaseEntity setBaseEntityFields(BaseEntity baseEntity) {
        if (baseEntity.id == null) {
            baseEntity.id = UIDHelper.getUID();
        }
        baseEntity.gi = RightsHelper.getMainGi();
        baseEntity.ui = VicThreadScope.ui.get();
        baseEntity.oi = VicThreadScope.oi.get() != null ? VicThreadScope.oi.get() : "";
        baseEntity.rights = RightsHelper.getDefault();
        baseEntity.extra = "Framework";
        baseEntity.cd = new Date();
        baseEntity.ud = new Date();
        baseEntity.active = true;
        return baseEntity;
    }

    public static BaseEntity setBaseEntityFieldsWithSimpleId(BaseEntity baseEntity) {
        baseEntity.id = UIDHelper.getSimpleID(baseEntity.getClass());
        baseEntity.gi = RightsHelper.getMainGi();
        baseEntity.ui = VicThreadScope.ui.get();
        baseEntity.oi = VicThreadScope.oi.get() != null ? VicThreadScope.oi.get() : "";
        baseEntity.rights = RightsHelper.getDefault();
        baseEntity.extra = "Framework";
        baseEntity.cd = new Date();
        baseEntity.ud = new Date();
        baseEntity.active = true;
        return baseEntity;
    }

    public static void overwriteJsonIgnoreFields(BaseEntity currentBaseEntity, BaseEntity oldBaseEntity) {
        if (oldBaseEntity == null) {
            return;
        }
        currentBaseEntity.oi = oldBaseEntity.oi;
        currentBaseEntity.gi = oldBaseEntity.gi;
        currentBaseEntity.ui = oldBaseEntity.ui;
        currentBaseEntity.rights = oldBaseEntity.rights;
        currentBaseEntity.cd = oldBaseEntity.cd;
        currentBaseEntity.ud = oldBaseEntity.ud;
        currentBaseEntity.active = oldBaseEntity.active;
    }

}
