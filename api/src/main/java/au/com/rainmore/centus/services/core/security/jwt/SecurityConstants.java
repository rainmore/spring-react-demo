package au.com.rainmore.centus.services.core.security.jwt;

public class SecurityConstants {

    public static final long   EXPIRATION_TIME           = 432_000_000; // 5 days in milliseconds
    public static final String AUTHORITIES               = "Authorities";
    public static final String TOKEN_PREFIX              = "Bearer ";
    public static final String JWT_TOKEN_HEADER          = "Jwt-Token";
    public static final String GET_ARRAYS_LLC            = "Rainmore";
    public static final String GET_ARRAYS_ADMINISTRATION = "Spring React Demo";
    public static final String FORBIDDEN_MESSAGE         = "Please login";

}
