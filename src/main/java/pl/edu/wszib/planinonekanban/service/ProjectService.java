package pl.edu.wszib.planinonekanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wszib.planinonekanban.service.model.Project;
import pl.edu.wszib.planinonekanban.service.repository.ProjectRepository;
import pl.edu.wszib.planinonekanban.service.exception.NotFoundException;
import pl.edu.wszib.planinonekanban.service.model.Status;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository dao;

    public Project get(Integer id) {
        return dao.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }

    public List<Project> list(Status status) {
        if (status == null) {
            return dao.findAll();
        } else {
            return dao.findAllByStatus(status);
        }
    }

    public void delete(Integer id) {
        dao.deleteById(id);
    }

    public Project create(Project newProfile) {
        newProfile.setId(null);
        return dao.save(newProfile);
    }

    public Project update(Project updateProfile) {
        Project existing = get(updateProfile.getId());
        return dao.save(updateProfile);
    }


}
