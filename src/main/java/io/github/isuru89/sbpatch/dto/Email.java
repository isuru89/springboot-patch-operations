package io.github.isuru89.sbpatch.dto;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Email {

    private String email;


}
