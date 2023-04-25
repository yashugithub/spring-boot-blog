package com.techtrain.dto;

import com.techtrain.constants.TenantType;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateTenantDTO {

    @NonNull
    private String name;

    @NonNull
    private TenantType type;

}
