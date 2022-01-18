package by.htp.ramanouski.taskmanager.security;



public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SING_UP_URL = "/organizations";
    public static final String CREATE_USER_URL = "/users/{organizationId}/organizations/{organizationId}";
    public static final String TOKEN_SECRET = "718EDB73667FE";
}
