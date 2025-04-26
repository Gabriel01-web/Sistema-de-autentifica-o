package com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.service;


import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model.DTO.UserCreateDTO;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model.DTO.UserUpdateDTO;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model.Enum.UserEnums;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model.User;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.repository.UserRepository;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.security.UserSpringSecurity;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.service.exceptions.AuthorizationException;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.service.exceptions.DataBindingViolationException;
import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.service.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findById(Long id){
        UserSpringSecurity userSpringSecurity = authorizated();
        if(!Objects.nonNull(userSpringSecurity) || !userSpringSecurity.hasRole(UserEnums.ADMIN) && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("acesso negado");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! id:" + id + ", Tipo:" + User.class.getName()));
    }

    @Transactional
    public User create(User obj){
        obj.setId(null);
        obj.setUsername(obj.getUsername());
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setUserEnums(Stream.of(UserEnums.NORMAL.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    public User update(User obj){
        User newobj = findById(obj.getId());
        newobj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newobj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    public static UserSpringSecurity authorizated(){
        try{
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch(Exception e){
            return null;
        }
    }

    public User fromDTO(@Valid UserCreateDTO obj) {
        User user = new User();
        user.setUsername(obj.getUserName());
        user.setPassword(obj.getPassword());
        return user;
    }

    public User fromDTO(@Valid UserUpdateDTO obj) {
        User user = new User();
        user.setId(obj.getId());
        user.setPassword(obj.getPassword());
        return user;
    }

}
