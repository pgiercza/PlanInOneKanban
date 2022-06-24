package pl.edu.wszib.planinonekanban.service.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.edu.wszib.planinonekanban.service.common.CrudResource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.ParseException;

@Entity
@Table(name = "Project")
public class Project implements CrudResource<Integer> {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    @Pattern(regexp = "[a-zA-Z]+")
    private String title;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    @Pattern(regexp = "[a-zA-Z]+")
    private String description;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MD-dd")
    @NotBlank
    private String duedate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Lob
    private byte[] picture;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) throws ParseException {
        this.duedate = duedate;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
