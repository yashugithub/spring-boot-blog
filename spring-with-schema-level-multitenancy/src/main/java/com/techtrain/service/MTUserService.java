package com.techtrain.service;

import com.techtrain.dto.CreateUserDTO;
import com.techtrain.tenant.entity.MTUser;
import com.techtrain.tenant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MTUserService extends AbstractTenantService{

    @Autowired
    UserRepository userRespository;

    public void createUserPerTenant(CreateUserDTO createUserDTO) throws Exception {

        if(isHeaderHasTenant(createUserDTO.getTenantName())) {
            MTUser savedUser = new MTUser();

            savedUser.setUserName(createUserDTO.getUserName());
            savedUser.setFirstName(createUserDTO.getFirstName());
            savedUser.setLastName(createUserDTO.getLastName());
            userRespository.save(savedUser);
        }
    }
}
