package pl.javadev.user;

import javax.validation.constraints.Min;

public class UserDeleteDto {
    @Min(value = 0)
    private Long id;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
