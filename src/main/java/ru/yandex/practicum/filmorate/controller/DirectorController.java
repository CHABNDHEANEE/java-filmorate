package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorDbService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Component
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorDbService directorDbService;

    @PostMapping("/directors")
    public Director addDirector(@Valid @RequestBody Director director) {
        return directorDbService.addDirector(director);
    }

    @PutMapping("/directors")
    public Director updateDirector(@Valid @RequestBody Director director) {
        return directorDbService.updateDirector(director);
    }

    @GetMapping("/directors/{id}")
    public Director getDirectorById(@PathVariable("id") int id) {
        return directorDbService.getDirectorById(id);
    }

    @GetMapping("/directors")
    public List<Director> getDirectorsList() {
        return directorDbService.getDirectorsList();
    }

    @DeleteMapping("/directors/{id}")
    public void deleteDirector(@PathVariable("id") int id) {
        directorDbService.deleteDirector(id);
    }
}
