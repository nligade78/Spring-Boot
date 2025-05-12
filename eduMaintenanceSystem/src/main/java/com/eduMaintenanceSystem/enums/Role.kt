package com.eduMaintenanceSystem.enums

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role {
    STUDENT, MENTOR;

    val authorities: List<GrantedAuthority>
        get() = listOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_" + this.name))
}