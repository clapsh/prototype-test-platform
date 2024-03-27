package gp.gameproto.dto;

import gp.gameproto.db.entity.Dibs;
import gp.gameproto.db.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Getter
public class GetFollowingResponse {
    private Integer followingLen;
    private List<FollowingResponse> followingList;

    public GetFollowingResponse(List<User> followings){
        List<FollowingResponse> myFollowingList = new ArrayList<>();
        for(User following:followings){
            //if (following.isPresent()){
                FollowingResponse dto = new FollowingResponse(following);
            //}
            myFollowingList.add(dto);
        }
        this.followingList = myFollowingList;
        this.followingLen = myFollowingList.size();
    }
}
