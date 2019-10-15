package pl.javadev.user;

import pl.javadev.lesson.Lesson;
import pl.javadev.userRole.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true, nullable = false, length = 250)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false, length = 250)
    private String indexNumber;
    private String firstName;
    private String lastName;
    private String year;
    private String major;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_lessons",
            joinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "id_lesson", referencedColumnName = "lesson_id")})
    private List<Lesson> lessons = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<UserRole> roles = new HashSet<>();

    User() {}

    public User(String email, String password, String indexNumber,
                String firstName, String lastName, String year, String major) {
        this.email = email;
        this.password = password;
        this.indexNumber = indexNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.major = major;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", indexNumber='" + indexNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", year='" + year + '\'' +
                ", major='" + major + '\'' +
                ", lessons=" + lessons +
                '}';
    }
}