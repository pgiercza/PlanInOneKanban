package pl.edu.wszib.planinonekanban.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.planinonekanban.service.model.Status;
import pl.edu.wszib.planinonekanban.service.model.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByStatus(Status status);
}
