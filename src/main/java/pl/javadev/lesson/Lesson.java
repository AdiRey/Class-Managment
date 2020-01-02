package pl.javadev.lesson;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.javadev.teacher.Teacher;
import pl.javadev.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lesson")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Lob
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "start", nullable = false)
    private LocalDateTime start;
    @Column(name = "end", nullable = false)
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToMany(mappedBy = "lessons")
    @Fetch(FetchMode.SELECT) // TODO Is it really necessary?
    private List<User> users = new ArrayList<>();


    public void addUser(User user) {
        users.add(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) &&
                Objects.equals(title, lesson.title) &&
                Objects.equals(description, lesson.description) &&
                Objects.equals(start, lesson.start) &&
                Objects.equals(end, lesson.end) &&
                Objects.equals(teacher, lesson.teacher) &&
                Objects.equals(users, lesson.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, start, end, teacher, users);
    }
}
