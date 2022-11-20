package hu.webuni.studentregister202210.service;

import hu.webuni.studentregister202210.dto.FacebookData;
import hu.webuni.studentregister202210.model.UserSecurity;
import hu.webuni.studentregister202210.repository.UserSecurityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class FacebookService {

    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    private final UserSecurityRepository userSecurityRepository;

    private static final String FB_URL="https://graph.facebook.com/v13.0";

    public String extractToken(OAuth2AuthenticationToken oAuth2AuthenticationToken){

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        OAuth2AuthorizedClient oAuth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient(authorizedClientRegistrationId, oAuth2AuthenticationToken.getName());
        log.info("TOKEN IS: "+ oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        findOrCreateUserByToken(oAuth2AuthenticationToken);

        return oAuth2AuthorizedClient.getAccessToken().getTokenValue();
    }

    private UserSecurity findOrCreateUserByToken(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        UserSecurity byFacebookId = userSecurityRepository.findByFacebookId(oAuth2AuthenticationToken.getPrincipal().getName());
        if (null == byFacebookId){
            byFacebookId = new UserSecurity(oAuth2AuthenticationToken.getPrincipal().getAttribute("email"),"", Set.of("USER"));
            byFacebookId.setFacebookId(oAuth2AuthenticationToken.getPrincipal().getName());
            userSecurityRepository.save(byFacebookId);
        }
       return byFacebookId;
    }
    private UserSecurity findOrCreateUserByFbData(FacebookData facebookData) {
        log.info("Facebookdata: ");
        log.info(facebookData.toString());

        UserSecurity byFacebookId = userSecurityRepository.findByFacebookId(String.valueOf(facebookData.getId()));
        if (null == byFacebookId){
            byFacebookId = new UserSecurity(facebookData.getEmail(), "", Set.of("USER"));
            byFacebookId.setFacebookId(String.valueOf(facebookData.getId()));
            userSecurityRepository.save(byFacebookId);
        }
        return byFacebookId;
    }


    public UserDetails getUserDetailsFromFBToken(String fbToken) {
            return findOrCreateUserByFbData(getDataFromFacebook(fbToken));
    }

    private FacebookData getDataFromFacebook(String fbToken){
       return  WebClient.create(FB_URL).get()
                .uri(uriBuilder -> {return uriBuilder.path("/me").queryParam("fields","email,name").build();})
                .headers(httpHeaders -> httpHeaders.setBearerAuth(fbToken))
                .retrieve()
                .bodyToMono(FacebookData.class)
                .block();
    }

}
