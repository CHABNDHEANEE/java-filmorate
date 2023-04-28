package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.DirectorDaoImpl;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Service
@Qualifier
@RequiredArgsConstructor
public class DirectorDbService {
    private final DirectorDaoImpl directorDao;

    public Director addDirector(Director director){
        return directorDao.addDirector(director);
    };

    public Director updateDirector(Director director){
        return directorDao.updateDirector(director);
    };

    public Director getDirectorById(int directorId){
        return directorDao.getDirectorById(directorId);
    };

    public List<Director> getDirectorsList(){
        return directorDao.getDirectorsList();
    };

    public void deleteDirector(int directorId){
        directorDao.deleteDirector(directorId);
    };
}
