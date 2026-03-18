package com.yash.inventory.util;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getClaims() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Unauthorized: No authentication found");
        }

        if (authentication.getPrincipal() instanceof Map<?, ?> claims) {
            return (Map<String, Object>) claims;
        }

        throw new RuntimeException("Invalid authentication principal");
    }

    public static Long getCurrentCompanyId() {
        return Long.valueOf(getClaims().get("companyId").toString());
    }

    public static Long getCurrentUserId() {
        return Long.valueOf(getClaims().get("userId").toString());
    }

    public static String getCurrentRole() {
        return getClaims().get("role").toString();
    }

    public static String getCurrentEmail() {
        return getClaims().get("sub").toString(); // subject
    }
}