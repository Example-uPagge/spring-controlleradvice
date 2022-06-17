package dev.struchkov.example.controlleradvice.controller;

import dev.struchkov.example.controlleradvice.domain.ErrorMessage;
import dev.struchkov.example.controlleradvice.domain.Person;
import dev.struchkov.example.controlleradvice.exception.NotFoundException;
import dev.struchkov.example.controlleradvice.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    // Ниже методы предназначенные для обработки исключений.

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

//    Вариант handleException без использованияResponseEntity.
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ErrorMessage handleException(NotFoundException exception) {
//        return new ErrorMessage(exception.getMessage());
//    }

    /**
     * Не будет вызываться для getByLogin, пока не будет закомментирован обработчик @ExceptionHandler(NotFoundException.class).
     * Но будет вызываться для getById
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    // Обработка ошибки преобразования
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorMessage handleException(MethodArgumentTypeMismatchException exception) {
        return new ErrorMessage(exception.getMessage());
    }

}
