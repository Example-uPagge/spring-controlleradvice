package dev.struchkov.example.controlleradvice.service;

import dev.struchkov.example.controlleradvice.domain.Person;
import dev.struchkov.example.controlleradvice.exception.NotFoundException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PersonService {

    private final Map<UUID, Person> persons = new HashMap<>();

    public PersonService() {
        final UUID komarId = UUID.randomUUID();
        persons.put(komarId, new Person(komarId, "komar", "Алексей", "ertyuiop"));
    }

    public Person getByLoginOrThrown(@NonNull String login) {
        return persons.values().stream()
                .filter(person -> person.getLogin().equals(login))
                .findFirst()
                .orElseThrow();
    }

    public Person getByIdOrThrown(@NonNull UUID id) {
        if (!persons.containsKey(id)) {
            throw new NotFoundException("Пользователь не найден");
        }
        return persons.get(id);
    }

}
