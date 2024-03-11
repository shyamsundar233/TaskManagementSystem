package com.taskswift.main.service;

import com.taskswift.main.entity.Roles;
import com.taskswift.main.entity.Tenant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionalService {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private RolesService rolesService;

    @Transactional
    public void generateAndInsertRanges() {
        if(tenantService.getTenantCount() == 0) {
            long startRange = 1;
            long endRange = 5000000;
            long rangeSize = 100000;

            while (startRange < endRange && startRange + rangeSize <= endRange) {
                Tenant tenant = new Tenant();
                tenant.setStartRange(startRange);
                tenant.setCurrentUniqueId(startRange);
                tenant.setEndRange(startRange + rangeSize);
                tenant.setActive(true);
                tenantService.addTenant(tenant);
                startRange = startRange + rangeSize + 1;
            }
        }
    }

    @Transactional
    public void populateRoles(){
        if(!rolesService.isRolesPopulated()){
            String[] roles = { "ROLE_ADMINISTRATOR", "ROLE_PROJECT_MANAGER", "ROLE_TEAM_LEADER", "ROLE_DEVELOPER", "ROLE_TESTER", "ROLE_CLIENT", "ROLE_OBSERVER", "ROLE_CONTRIBUTOR", "ROLE_ANALYST", "ROLE_GUEST" };
            for(String role : roles){
                Roles rolesObj = new Roles();
                rolesObj.setRoleName(role);
                rolesService.saveRoles(rolesObj);
            }
        }
    }

}
