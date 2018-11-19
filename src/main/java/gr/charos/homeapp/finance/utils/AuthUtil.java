package gr.charos.homeapp.finance.utils;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;

public class AuthUtil {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(AuthUtil.class);

    public static void authenticate(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        String username = userProfile.getUsername();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("User {} {} connected.", userProfile.getFirstName(), userProfile.getLastName());
    }
}
