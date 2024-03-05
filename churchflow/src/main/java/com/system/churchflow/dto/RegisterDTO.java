package com.system.churchflow.dto;

import com.system.churchflow.enums.UserRole;



public record RegisterDTO (String login, String password, String name, UserRole role,byte[] picture){

}
