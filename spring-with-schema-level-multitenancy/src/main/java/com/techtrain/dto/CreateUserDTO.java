package com.techtrain.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserDTO {

    @NonNull
    private String userName;

    @NonNull

    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String tenantName;
}
