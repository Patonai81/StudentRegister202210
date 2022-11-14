package hu.webuni.studentregister202210.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.webuni.studentregister202210.model.UserSecurity;
import hu.webuni.studentregister202210.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    UserSecurityRepository userSecurityRepository;

    public static final Algorithm MYSECRET = Algorithm.HMAC256("mysecret");
    public static final String STUDENT_APP = "StudentApp";
    public static final String AUTH = "auth";

    public String createJwTToken(UserDetails principal) {

      return  JWT.create().withSubject(principal.getUsername())
                .withArrayClaim(AUTH,principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(2)))
                .withIssuer(STUDENT_APP)
                .sign(MYSECRET);
    }


    public UserSecurity parseJWT(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(MYSECRET).withIssuer(STUDENT_APP).build().verify(jwtToken);
        UserSecurity userSecurity= userSecurityRepository.findByUserName(decodedJWT.getSubject());
        return userSecurity;
        //return new User(decodedJWT.getSubject(),"",decodedJWT.getClaim(AUTH).asList(String.class).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
