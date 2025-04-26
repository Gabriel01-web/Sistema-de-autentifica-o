package com.APIRestfulSystemofInvestiments.SystemInvestimentsAutentication.model.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum UserEnums {

    ADMIN(1,"ROLE_ADMIN"),
    NORMAL(2,"ROLE_NORMAL");

    private Integer code;
    private String description;

    public static UserEnums toEnum(Integer code){
        if(Objects.isNull(code))
            return null;
        for(UserEnums x : UserEnums.values()){
            if(code.equals(x.getCode()))
                return x;
        }
        throw new IllegalArgumentException("Invalid code:" + code);
    }
}