package pl.javadev.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserPasswordDto {
    private Long id;
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