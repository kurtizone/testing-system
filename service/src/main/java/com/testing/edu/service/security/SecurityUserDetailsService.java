package com.testing.edu.service.security;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Service
public class SecurityUserDetailsService extends JdbcDaoImpl {

    Logger logger = Logger.getLogger(SecurityUserDetailsService.class);

    public static final String DEF_USERS_BY_USERNAME_QUERY = "select password, enable from USER where username = ?";
    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY = "select u.username as username, u.user_role as authority" +
            " from USER u" +
            " where u.username = ?";

    @Autowired
    private DataSource dataSource;

    public SecurityUserDetailsService() {
        super();
        setUsersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY);
        setAuthoritiesByUsernameQuery(DEF_AUTHORITIES_BY_USERNAME_QUERY);
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return this.getJdbcTemplate().query(getUsersByUsernameQuery(), new String[]{username}, (rs, rowNum) -> {
            String password = rs.getString(1);
            boolean enabled = rs.getBoolean(2);
            logger.info("loadUserbyUsername");
            logger.info(password);
            logger.info(username);
            logger.info(enabled);
            return new CustomUserDetails(username, password, enabled);
        });
    }

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userDetails, List<GrantedAuthority> authorities) {
        Class<? extends UserDetails> userDetailsClass = userDetails.getClass();

        if (!userDetailsClass.equals(CustomUserDetails.class)) {
            throw new InternalAuthenticationServiceException("Provided UserDetails is incorrect: " + userDetailsClass);
        }
        logger.info("--priviosly set authorities");
        logger.info(userDetails.getUsername());
        logger.info(userDetails.getPassword());
        logger.info(userDetails.getAuthorities());
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        customUserDetails.setAuthorities(authorities);
        logger.info("--after set authorities");
        logger.info(customUserDetails.getUsername());
        logger.info(customUserDetails.getPassword());
        logger.info(customUserDetails.getAuthorities());
        return customUserDetails;
    }

    /**
     * Provide additional information about company(organization) where user
     * works except SYS_ADMIN role.
     */
    public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {

        Logger logger = Logger.getLogger(CustomUserDetails.class);

        @Setter
        private List<GrantedAuthority> authorities;


        public CustomUserDetails(String username, String password, boolean enabled) {
            super(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
            logger.info("--customConstructor");
            logger.info(username);
            logger.info(password);
            logger.info(enabled);
        }

        @Override
        public List<GrantedAuthority> getAuthorities() {
            return authorities;
        }
    }
}
