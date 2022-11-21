package hu.webuni.studentregister202210.service;

import hu.webuni.studentregister202210.dto.FacebookData;
import hu.webuni.studentregister202210.dto.GoogleData;
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

import java.net.URI;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommunityLoginService {

    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    private final UserSecurityRepository userSecurityRepository;

    private static final String FB_URL="https://graph.facebook.com/v13.0";
    private static final String GOOGLE_URL="https://www.googleapis.com/oauth2/v3";
    private static final String GOOGLE_URL1="https://oauth2.googleapis.com";

    public String extractToken(OAuth2AuthenticationToken oAuth2AuthenticationToken){

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        OAuth2AuthorizedClient oAuth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient(authorizedClientRegistrationId, oAuth2AuthenticationToken.getName());
        log.info("TOKEN IS: "+ oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        log.info("URL IS:"+oAuth2AuthorizedClient.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri());

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
    private UserSecurity findOrCreateUserByFaceBookId(String id, String email) {
        UserSecurity userSecurity = userSecurityRepository.findByFacebookId(id);
        if (null == userSecurity){
            userSecurity = new UserSecurity(email, "", Set.of("USER"));
            userSecurity.setFacebookId(String.valueOf(id));
            userSecurityRepository.save(userSecurity);
        }
        return userSecurity;
    }

    private UserSecurity findOrCreateUserByGoogleId(String id, String email) {
        UserSecurity userSecurity = userSecurityRepository.findByGoogleId(id);
        if (null == userSecurity){
            userSecurity = new UserSecurity(email, "", Set.of("USER"));
            userSecurity.setGoogleId(String.valueOf(id));
            userSecurityRepository.save(userSecurity);
        }
        return userSecurity;
    }


    public UserDetails getUserDetailsFromFBToken(String fbToken) {
          FacebookData facebookData = getDataFromFacebook(fbToken);
          log.info("Facebook data: ");
          log.info(facebookData.toString());
          return findOrCreateUserByFaceBookId(String.valueOf(facebookData.getId()), facebookData.getEmail());
    }

    public UserDetails getUserDetailsFromGoogleToken(String googleToken) {

       GoogleData googleData = getDataFromGoogle(googleToken);
       log.info("Google data:");
       log.info(googleData.toString());
       return findOrCreateUserByGoogleId(googleData.getSub(), googleData.getEmail());
    }


    private FacebookData getDataFromFacebook(String fbToken){
       return  WebClient.create(FB_URL).get()
                .uri(uriBuilder -> {return uriBuilder.path("/me").queryParam("fields","email,name").build();})
                .headers(httpHeaders -> httpHeaders.setBearerAuth(fbToken))
                .retrieve()
                .bodyToMono(FacebookData.class)
                .block();
    }

    private GoogleData getDataFromGoogle(String googleToken){
        return  WebClient.create(GOOGLE_URL).get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder.path("/userinfo").build();
                    log.info("URI IS:");
                    log.info(uri.toString());
                    return uri;
                })
                .headers(httpHeaders -> httpHeaders.setBearerAuth(googleToken))
                .retrieve()
                .bodyToMono(GoogleData.class)
                .block();
    }


}
