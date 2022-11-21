package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.service.CommunityLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FacebookLoginController {

    @Autowired
    CommunityLoginService communityLoginService;

    @RequestMapping("/fbLoginSuccess")
    public String onFacebookLoginSuccess(Map<String,Object> model, OAuth2AuthenticationToken oAuth2AuthenticationToken
    , @AuthenticationPrincipal OAuth2User user){
        String fullName = oAuth2AuthenticationToken.getPrincipal().getAttribute("name");
        String token = communityLoginService.extractToken(oAuth2AuthenticationToken);
        model.put("fullName",fullName);
        model.put("token",token);
        return "home";
    }
}
