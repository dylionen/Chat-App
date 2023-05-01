package pl.training.chat.model;

import jakarta.persistence.*;
import lombok.*;
import pl.training.chat.login.User;

import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    private Date refreshDate;

    public LoggedUser setId(Long id) {
        this.id = id;
        return this;
    }

    public LoggedUser setRefreshDate(Date refreshDate) {
        this.refreshDate = refreshDate;
        return this;
    }


    public LoggedUser setUser(User user) {
        this.user = user;
        return this;
    }
}
