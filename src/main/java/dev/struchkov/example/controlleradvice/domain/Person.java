package dev.struchkov.example.controlleradvice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private UUID id;
    private String login;
    private String firstName;
    private String password;

}
