package pl.javadev.user;

import pl.javadev.userRole.UserRole;
import pl.javadev.userRole.UserRoleDto;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private Long id;
    private String email;
    private String indexNumber;
    private String firstName;
    private String lastName;
    private String year;
    private String major;
    private Set<UserRoleDto> roles = new HashSet<>();

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

    public Set<UserRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoleDto> roles) {
        this.roles = roles;
    }
}

