package com.giacomo.potenza.InnovationFintech.dto;

import lombok.*;
import com.giacomo.potenza.InnovationFintech.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String nome;
    private String cognome;
    private String matricola;
    private String password;
    private String email;
    private String ruolo;
}
