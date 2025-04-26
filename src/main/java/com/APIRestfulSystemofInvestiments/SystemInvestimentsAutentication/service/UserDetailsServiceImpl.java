package com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.service;

import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model.User;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.repository.UserRepository;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if(Objects.isNull(user))
            throw new UsernameNotFoundException("Usuário não encontrado");
        return new UserSpringSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getUserEnums());
    }
}