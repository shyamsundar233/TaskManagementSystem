package com.taskswift.main.util;

import com.taskswift.main.entity.Tenant;
import com.taskswift.main.service.TenantService;
import com.taskswift.main.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class TenantUtil {

    private static TenantService tenantService;

    private static UserService userService;

    public TenantUtil(TenantService tenantService, UserService userService){
        TenantUtil.tenantService = tenantService;
        TenantUtil.userService = userService;
    }

    public static Tenant getTenantForNewUser(){
        Long userId = UserUtil.getCurrentUserId();
        if(userId == null){
            return tenantService.getNextActiveTenant();
        }else{
            return userService.getUserById(userId).getTenant();
        }
    }

    public static void saveTenant(Tenant tenant){
        tenantService.addTenant(tenant);
    }

}
