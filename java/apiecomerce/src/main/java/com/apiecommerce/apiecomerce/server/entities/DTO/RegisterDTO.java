package com.apiecommerce.apiecomerce.server.entities.DTO;

import com.apiecommerce.apiecomerce.server.entities.Roles;

public record RegisterDTO(String username, String password, Roles role) {

}
