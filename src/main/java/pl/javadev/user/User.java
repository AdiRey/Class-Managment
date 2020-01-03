package pl.javadev.user;

import pl.javadev.lesson.Lesson;
import pl.javadev.userRole.UserRole;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    @Column(name = "email", unique = true, nullable = false, length = 250)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(unique = true, nullable = false, length = 250)
    private String indexNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "grade")
    private String grade;
    @Column(name = "major")
    private String major;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_lessons",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "lesson_id", referencedColumnName = "id_lesson")})
    private List<Lesson> lessons = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<UserRole> roles = new HashSet<>();

    public void addRole(UserRole role) {
        this.roles.add(role);
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(indexNumber, user.indexNumber) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(grade, user.grade) &&
                Objects.equals(major, user.major) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, indexNumber, firstName, lastName, grade, major, roles);
    }
}