package pl.training.chat.model;

import lombok.Data;

import java.io.File;
import java.util.Date;

@Data
public class Message {
    private String message;
    private String fromLogin;
    private Date sendDate;
    private String image;

}
