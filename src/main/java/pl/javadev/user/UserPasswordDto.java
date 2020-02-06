package pl.javadev.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserPasswordDto {
    private Long id;
    private String email;
    private String indexNumber;
    @Size(min = 6)
    @NotEmpty
    private String oldPassword;
    @Size(min = 6)
    @NotEmpty
    private String password;
    @Size(min = 6)
    @NotEmpty
    private String repeatedPassword;

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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}