package pl.training.chat.model;

import lombok.Data;

@Data
public class LoggedUserView {
    private String userName;
    private String avatar;

    public LoggedUserView setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public LoggedUserView setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
