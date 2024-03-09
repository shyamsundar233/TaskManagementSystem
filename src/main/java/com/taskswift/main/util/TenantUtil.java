package com.taskswift.main.util;

import com.taskswift.main.entity.Tenant;
import com.taskswift.main.service.TenantService;
import org.springframework.stereotype.Component;

@Component
public class TenantUtil {

    private static TenantService tenantService;

    public TenantUtil(TenantService tenantService){
        TenantUtil.tenantService = tenantService;
    }

    public static void configureTenantForUser(){
        Long userId = UserUtil.getCurrentUserId();
        if(userId == null){
            Tenant tenant = new Tenant();
        }else{

        }
    }

}
