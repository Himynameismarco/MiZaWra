package com.project.mizawra.rest;

import com.project.mizawra.common.event.OnForgetPasswordEvent;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.AuthRequest;
import com.project.mizawra.models.dto.SavePasswordDto;
import com.project.mizawra.service.ClientService;
import com.project.mizawra.service.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterRestController {
    private final ApplicationEventMulticaster eventMulticaster;
    private final ClientService clientService;
    private final DaoAuthenticationProvider authenticationProvider;
    private final JwtTokenService jwtTokenService;

    public RegisterRestController(ApplicationEventMulticaster eventMulticaster, ClientService clientService,
                                  DaoAuthenticationProvider authenticationProvider, JwtTokenService jwtTokenService) {
        this.eventMulticaster = eventMulticaster;
        this.clientService = clientService;
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthRequest request) {
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenService.createToken(request.getUsername())).body(jwtTokenService.createToken(request.getUsername()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Object> forgetPassword(@RequestParam("email") String email, HttpServletRequest request) throws BadCredentialsException {
        Client client = clientService.getClient(email);
        if (client == null) {
            return ResponseEntity.badRequest().build();
        }

        eventMulticaster.multicastEvent(new OnForgetPasswordEvent(client, request.getLocale()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/savePassword")
    public ResponseEntity<Object> savePassword(SavePasswordDto passwordDto) {
        VerificationToken token = clientService.getVerificationToken(passwordDto.getTokenId());
        if (token == null || clientService.isTokenExpired(token)) {
            return ResponseEntity.badRequest().build();
        }
        clientService.changeClientPassword(token.getClient(), passwordDto.getPassword());
        clientService.deleteVerificationToken(passwordDto.getTokenId());
        return ResponseEntity.ok().build();
    }
}
