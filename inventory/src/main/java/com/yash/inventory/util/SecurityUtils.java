package com.yash.inventory.util;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    @SuppressWarnings("unchecked")
    public static Long getCurrentCompanyId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Unauthorized: No authentication found");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Map<?, ?> claims) {
            Object companyIdObj = claims.get("companyId");
            if (companyIdObj == null) {
                throw new RuntimeException("Company ID not found in token");
            }
            return Long.valueOf(companyIdObj.toString());
        }

        throw new RuntimeException("Invalid authentication principal");
    }

    @SuppressWarnings("unchecked")
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Map<?, ?> claims) {
            Object userIdObj = claims.get("userId");
            if (userIdObj == null)
                throw new RuntimeException("User ID not found in token");
            return Long.valueOf(userIdObj.toString());
        }
        throw new RuntimeException("Invalid authentication principal");
    }

    @SuppressWarnings("unchecked")
    public static String getCurrentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Map<?, ?> claims) {
            Object roleObj = claims.get("role");
            if (roleObj == null)
                throw new RuntimeException("Role not found in token");
            return roleObj.toString();
        }
        throw new RuntimeException("Invalid authentication principal");
    }
}