package gp.gameproto.service;

import gp.gameproto.db.entity.Follow;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
   /* @Transactional(readOnly = true)
    public void followUser(Long followingId, User follower){
        // 팔로워와 팔로잉이 같은 경우
        if(followingId == follower.getId()){
            throw new IllegalArgumentException("impossible");
        }
        // 팔로우가 되어있는 경우
        //Optional<Follow> checkFollow = followRepository.findByFollowerAndFollowing()
    }*/
}
