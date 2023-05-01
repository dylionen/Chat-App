package pl.training.chat.login;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    @ManyToMany(mappedBy="roles")
    private List<User> users;
}