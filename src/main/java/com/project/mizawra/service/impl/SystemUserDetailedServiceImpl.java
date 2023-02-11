package com.project.mizawra.service.impl;

import com.project.mizawra.dao.ClientRepository;
import com.project.mizawra.models.Client;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class SystemUserDetailedServiceImpl implements UserDetailsService {
    private final ClientRepository clientRepository;

    public SystemUserDetailedServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> optionalClient = clientRepository.findByEmail(username);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            if (!client.isActive()) {
                throw new UsernameNotFoundException("User is not active");
            }

            //TODO think do we need authorities or not (3 parameter in constructor)
            return new User(client.getEmail(), client.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User is not found");
    }
}
