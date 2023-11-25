package com.project.mizawra.rest;

import com.project.mizawra.common.event.OnForgetPasswordEvent;
import com.project.mizawra.models.Client;
import com.project.mizawra.models.VerificationToken;
import com.project.mizawra.models.dto.AuthRequest;
import com.project.mizawra.models.dto.SavePasswordDto;
import com.project.mizawra.service.ClientService;
import com.project.mizawra.service.JwtTokenService;
import com.project.mizawra.service.VerificationTokenService;
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
public class LoginController {
    private final ApplicationEventMulticaster eventMulticaster;
    private final ClientService clientService;
    private final VerificationTokenService verificationTokenService;
    private final DaoAuthenticationProvider authenticationProvider;
    private final JwtTokenService jwtTokenService;

    public LoginController(ApplicationEventMulticaster eventMulticaster, ClientService clientService,
                           VerificationTokenService verificationTokenService,
                           DaoAuthenticationProvider authenticationProvider, JwtTokenService jwtTokenService) {
        this.eventMulticaster = eventMulticaster;
        this.clientService = clientService;
        this.verificationTokenService = verificationTokenService;
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthRequest request) {
        Client client = clientService.getClient(request.getUsername());
        if (client == null || !client.getActive()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenService.createToken(request.getUsername())).body(jwtTokenService.createToken(request.getUsername()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Object> forgetPassword(@RequestParam("email") String email, HttpServletRequest request) throws
            BadCredentialsException {
        Client client = clientService.getClient(email);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        eventMulticaster.multicastEvent(new OnForgetPasswordEvent(client, request.getLocale()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/savePassword")
    public ResponseEntity<Object> savePassword(@RequestBody @Valid SavePasswordDto passwordDto) {
        VerificationToken token = verificationTokenService.getVerificationToken(passwordDto.getTokenId());
        if (token == null || verificationTokenService.isTokenExpired(token)) {
            return ResponseEntity.badRequest().build();
        }
        clientService.changeClientPassword(token.getClient(), passwordDto.getPassword());
        verificationTokenService.deleteVerificationToken(passwordDto.getTokenId());
        return ResponseEntity.ok().build();
    }
}
