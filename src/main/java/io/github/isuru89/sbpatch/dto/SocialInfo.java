package io.github.isuru89.sbpatch.dto;

import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class SocialInfo {

    @ManyToOne
    private UserDTO user;

    private String githubProfile;
    private String twitterProfile;
    private String linkedInProfile;

}
