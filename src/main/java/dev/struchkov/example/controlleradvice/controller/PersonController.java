package dev.struchkov.example.controlleradvice.controller;

import dev.struchkov.example.controlleradvice.domain.Person;
import dev.struchkov.example.controlleradvice.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<Person> getByLogin(@RequestParam("login") String login) {
        return ResponseEntity.ok(personService.getByLoginOrThrown(login));
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(personService.getById(id).orElseThrow());
    }

}
