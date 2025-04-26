package com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model;


import com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model.Enum.UserEnums;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name= "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, nullable = false,length = 80)
    @Size(min = 8, max = 80)
    @NotBlank
    private String username;

    @Column(name = "senha", nullable = false,length = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 60)
    @NotBlank
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "nivel_permissão")
    private Set<Integer> userEnums = new HashSet<>();

    public Set<UserEnums> getUserEnums() {
        return this.userEnums.stream().map(x -> UserEnums.toEnum(x)).collect(Collectors.toSet());
    }

    public void addUserEnums(UserEnums userEnums){
        this.userEnums.add(userEnums.getCode());
    }

}
