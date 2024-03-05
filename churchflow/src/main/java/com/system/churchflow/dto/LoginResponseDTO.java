package com.system.churchflow.dto;

import com.system.churchflow.enums.UserRole;

public record LoginResponseDTO(String token, String email, String name, UserRole role, byte[] picture) {


}
