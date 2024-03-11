package com.taskswift.main.util;

import com.taskswift.main.entity.Tenant;
import com.taskswift.main.exception.TenantException;
import com.taskswift.main.service.TenantService;
import com.taskswift.main.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class TenantUtil {

    private static TenantService tenantService;

    public static Tenant currentTenant;

    public TenantUtil(TenantService tenantService, UserService userService){
        TenantUtil.tenantService = tenantService;
        TenantUtil.currentTenant = new Tenant();
    }

    public static Tenant getNextActiveTenant(){
        return tenantService.getNextActiveTenant();
    }

    public static Long getNextUniqueId(){
        Long nextUniqueId = null;
        Tenant tenant = tenantService.getTenantById(currentTenant.getTenantId());
        if(tenant != null){
            if(tenant.getCurrentUniqueId() <= tenant.getEndRange()){
                nextUniqueId = tenant.getCurrentUniqueId();
                tenant.setCurrentUniqueId(nextUniqueId + 1);
                TenantUtil.saveTenant(tenant);
                return nextUniqueId;
            }else{
                throw new TenantException("Tenant Range Exceeded");
            }
        }else{
            throw new TenantException("Invalid Tenant");
        }
    }

    public static void saveTenant(Tenant tenant){
        tenantService.addTenant(tenant);
    }

}
