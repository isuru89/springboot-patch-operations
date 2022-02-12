package io.github.isuru89.sbpatch.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    private String firstName;
    private String lastName;

    private String primaryEmail;

    @ElementCollection
    @CollectionTable(name = "user_emails")
    private List<Email> secondaryEmails;

    private Long createdAt;
    private Long updatedAt;
}
