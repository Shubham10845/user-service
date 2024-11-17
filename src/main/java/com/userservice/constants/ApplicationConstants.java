package com.userservice.constants;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public final class ApplicationConstants {
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final String ISSUER = "Shubham Shukla";
    public static final String SUBJECT = "JWT Token";
    public static final long EXPIRATION_TIME = 3600_000;

}
