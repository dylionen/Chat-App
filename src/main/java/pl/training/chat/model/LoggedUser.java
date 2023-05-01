package pl.training.chat.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    private Date refreshDate;

    public LoggedUser setId(Long id) {
        this.id = id;
        return this;
    }

    public LoggedUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoggedUser setRefreshDate(Date refreshDate) {
        this.refreshDate = refreshDate;
        return this;
    }
}
