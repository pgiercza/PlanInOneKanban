package pl.edu.wszib.planinonekanban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wszib.planinonekanban.service.ProjectService;
import pl.edu.wszib.planinonekanban.service.exception.NotFoundException;
import pl.edu.wszib.planinonekanban.service.model.Status;
import pl.edu.wszib.planinonekanban.service.model.Project;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String defaultView() {
        return "homePage";
    }

    @GetMapping("/get")
    public String getProjectView(@RequestParam Integer id, Model model) {
        model.addAttribute("project", projectService.get(id));
        return "singleProjectPage";
    }

    @GetMapping("/list")
    public String listProjectsView(@RequestParam(required = false) Status status, Model model) throws ParseException {
        model.addAttribute("projects", projectService.list(status));
        model.addAttribute("statuses", Status.values());
        return "listProjectPage";
    }

    @GetMapping("/create")
    public String createProjectView(Model model) {
        model.addAttribute("newProject", new Project());
        model.addAttribute("statuses", Status.values());
        return "createProjectPage";
    }

    @GetMapping("/update")
    public String updateProjectView(@RequestParam Integer id, Model model) {
        model.addAttribute("updateProject", projectService.get(id));
        model.addAttribute("statuses", Status.values());
        return "updateProjectPage";
    }

    @GetMapping("/delete")
    public String deleteProjectView(@RequestParam Integer id, Model model) {
        model.addAttribute("project", projectService.get(id));
        return "deleteProjectPage";
    }

    @PostMapping("/create")
    public String createProjectAction(@Valid Project newProject, BindingResult bindingResult, @RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        if (multipartFile.getBytes().length == 0) {
            bindingResult.addError(new FieldError("newProject", "picture", "Picture is required!"));
            model.addAttribute("statuses", Status.values());
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(newProject);
            model.addAttribute("org.springframework.validation.BindingResult.newProject", bindingResult);
            return "createProjectPage";
        }

        newProject.setPicture(multipartFile.getBytes());
        newProject = projectService.create(newProject);
        return "redirect:/project/list";
    }

    @PostMapping("/update")
    public String updateProjectAction(@RequestParam Integer id, @RequestParam("file") MultipartFile multipartFile, @Valid Project updateProject, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute(updateProject);
            model.addAttribute("org.springframework.validation.BindingResult.updateProject", bindingResult);
            model.addAttribute("statuses", Status.values());
            return "updateProjectPage";
        }

        if (multipartFile.getBytes().length != 0) {
            updateProject.setPicture(multipartFile.getBytes());
        } else {
            updateProject.setPicture(projectService.get(id).getPicture());
        }
        updateProject = projectService.update(updateProject);
        return "redirect:/project/get?id=" + updateProject.getId();
    }


    @PostMapping("/delete")
    public String deleteProjectAction(@RequestParam Integer id, Model model) {
        projectService.delete(id);
        return "redirect:/project/list";
    }

    @ResponseBody
    @GetMapping(value = "/picture", produces = "image/png")
    public byte[] picture(@RequestParam Integer id) {
        return projectService.get(id).getPicture();
    }

    @ExceptionHandler(NotFoundException.class)
    public String notFoundView() {
        return "errorPage";
    }
}
