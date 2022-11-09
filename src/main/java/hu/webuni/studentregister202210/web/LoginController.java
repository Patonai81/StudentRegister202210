package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.dto.LoginDTO;
import hu.webuni.studentregister202210.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/login")
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;



    @PostMapping
    public String login(@RequestBody LoginDTO loginDTO){
        log.debug(loginDTO.toString());

      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(),loginDTO.getPassword()));
      return jwtService.createJwTToken((UserDetails)authentication.getPrincipal());
    }
}
