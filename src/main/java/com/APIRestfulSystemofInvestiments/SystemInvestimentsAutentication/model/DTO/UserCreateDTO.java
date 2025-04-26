package com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateDTO {

    @Size(min = 8, max = 80)
    @NotBlank
    private String userName;

    @Size(min = 8, max = 60)
    @NotBlank
    private String password;
}
