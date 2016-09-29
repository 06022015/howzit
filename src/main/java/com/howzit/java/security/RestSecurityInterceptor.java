package com.howzit.java.security;

import com.howzit.java.bl.UserBL;
import com.howzit.java.exception.AuthenticationException;
import com.howzit.java.exception.CredentialNotFoundException;
import com.howzit.java.exception.HowzitException;
import com.howzit.java.model.LoginStatusEntity;
import com.howzit.java.model.UserEntity;
import com.howzit.java.model.type.PasswordType;
import com.howzit.java.util.CommonUtil;
import com.howzit.java.util.Constants;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/18/14
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestSecurityInterceptor implements ContainerRequestFilter, Constants {

    private Logger logger = LoggerFactory.getLogger(RestSecurityInterceptor.class);

    private UserBL userBL = (UserBL)CommonUtil.getBean("userBL");

    private CommonUtil commonUtil = (CommonUtil)CommonUtil.getBean("commonUtil");

    private PasswordEncoder passwordEncoder = (PasswordEncoder)CommonUtil.getBean("passwordEncoder");


    public ContainerRequest filter(ContainerRequest containerRequest) {
        processMediaType(containerRequest);
        String requestURI = containerRequest.getRequestUri().toString();
        if(!requestURI.contains("/ann/")){
            checkToken(containerRequest);
        }
        return containerRequest;
    }

    private void processMediaType(ContainerRequest containerRequest) {
        String requestUri = containerRequest.getRequestUri().toString();
        if (requestUri.contains(".xml")) {
            requestUri = requestUri.replace(".xml", "");
        } else if (requestUri.contains(".json")) {
            requestUri = requestUri.replace(".json", "");
            containerRequest.getRequestHeaders().putSingle("accept", MediaType.APPLICATION_JSON);
        }
        containerRequest.setUris(containerRequest.getBaseUri(), UriBuilder.fromUri(requestUri).build());
    }

    private void checkToken(ContainerRequest containerRequest) {
        List<String> securityToken = containerRequest.getRequestHeader(SECURITY_TOKEN);
        if (null == securityToken || securityToken.isEmpty()) {
            throw new CredentialNotFoundException("Token not found");
        }
        //String token = new String(Base64.decode(securityToken.get(0)));
        UserEntity user = userBL.validateAuthentication(securityToken.get(0));
        if(user.getPasswordType().equals(PasswordType.TEMPORARY) && !containerRequest.getRequestUri().toString().contains("/change/password"))
            throw new HowzitException(HttpStatus.TEMPORARY_REDIRECT.value(),commonUtil.getText("change.password.message"));
        containerRequest.setSecurityContext(new Authorizer(user));
    }

    private void checkUsernameAndPassword(ContainerRequest containerRequest) {
        List<String> authorization = containerRequest.getRequestHeader(AUTHORIZATION_PROPERTY);
        if (null == authorization || authorization.isEmpty()) {
            throw new CredentialNotFoundException("Username and password not found");
        }
        String encodeUsernameAndPassword = authorization.get(0).replace(AUTHENTICATION_SCHEME, "");
        String usernameAndPassword = new String(Base64.decode(encodeUsernameAndPassword));
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
        UserEntity user = userBL.loadUserByUsername(username);
        if (passwordEncoder.isPasswordValid(password, user.getPassword(), null)) {
            containerRequest.setSecurityContext(new Authorizer(user));
        } else {
            throw new AuthenticationException("Invalid credential");
        }
    }
}
