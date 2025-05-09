package com.Test.Service;


import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
    
    private static final String AUTH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiZXhwIjoxNzQ2Nzk1NTI1LCJpYXQiOjE3NDY3OTUyMjUsImlzcyI6IkFmZm9yZG1lZCIsImp0aSI6IjJiNDE2ODQ1LWYxOTUtNGQ3OS04ZjlkLThjNmNkOTEzNmI5OSIsInN1YiI6ImFub3VzaGthLjIyMjZjc2VtbDExQGtpZXQuZWR1In0sImVtYWlsIjoiYW5vdXNoa2EuMjIyNmNzZW1sMTFAa2lldC5lZHUiLCJuYW1lIjoiYW5vdXNoa2EgZ29lbCIsInJvbGxObyI6IjIyMDAyOTE1MzAwMTgiLCJhY2Nlc3NDb2RlIjoiU3hWZWphIiwiY2xpZW50SUQiOiIyYjQxNjg0NS1mMTk1LTRkNzktOGY5ZC04YzZjZDkxMzZiOTkiLCJjbGllbnRTZWNyZXQiOiJWVWNNcGRFcEV6ZU5aTU1EIn0.p_R-BUgGOnRTFeh0rV-Gf4XhHQflUjJ8iE_YScdgIrU";
    private static final long TOKEN_EXPIRY = 1746795525L; 
    public AuthService() {
        log.info("Using hardcoded authentication token");
    }

    public String getAuthToken() {
        if (System.currentTimeMillis() / 1000 >= TOKEN_EXPIRY - 300) {
            log.warn("Hardcoded token is expired or about to expire. Please generate a new one.");
        }
        return AUTH_TOKEN;
    }
}