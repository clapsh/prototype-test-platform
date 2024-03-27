package gp.gameproto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateFollowRequest {
    private String followerEmail;
    private String followingEmail;
}
