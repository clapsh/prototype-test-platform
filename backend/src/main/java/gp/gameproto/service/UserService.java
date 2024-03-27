package gp.gameproto.service;

import gp.gameproto.db.entity.Test;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.repository.UserRepository;
import gp.gameproto.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public String save(AddUserRequest dto){
        if (userRepository.findByEmail(dto.getEmail()).isPresent()){
            return "이메일이 존재합니다.";
        }
        userRepository.save(User.builder()
                .email(dto.getEmail())
                .pw(bCryptPasswordEncoder.encode(dto.getPw()))
                .name(dto.getName())
                .bio(dto.getBio())
                .gender(dto.getGender())
                .nation(dto.getNation())
                .age(dto.getAge())
                .imgPath(dto.getImgPath())
                .createdAt(dto.getCreatedAt())
                .favCategory1(dto.getFavCategory1())
                .favCategory2(dto.getFavCategory2())
                .favCategory3(dto.getFavCategory3())
                .build());
        return "회원가입 성공";
    }

    @Transactional(readOnly=true)
    public User login(UserLoginRequest dto){
        /*Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty()){
            new IllegalArgumentException("not found: "+dto.getEmail());
            //return "해당 유저를 찾지 못했습니다.";
        }*/
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("not found: "+dto.getEmail()));
        if (!bCryptPasswordEncoder.matches(dto.getPw(), user.getPassword())){
            new BadCredentialsException("not match password!");
        }
        return user;
        //return "비밀번호가 일치하지 않습니다.";
    }

    @Transactional(readOnly=true)
    public User findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));
        return user;
    }

    // 회원정보 수정
    @Transactional
    public User updateInfo(Long userid, UpdateUserRequest request){
        User user = userRepository.findById(userid)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+userid));

        user.updateInfo(
                request.getName(),
                request.getGender(),
                request.getAge(),
                request.getNation(),
                request.getBio(),
                request.getFavCategory1(),
                request.getFavCategory2(),
                request.getFavCategory3()
        );

        return user;
    }

    @Transactional
    public User updateBio (Long userid, UpdateUserBioRequest request){
        User user = userRepository.findById(userid)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ userid));

        user.updateBio(request.getBio());
        return user;
    }

    @Transactional
    public User updateName (Long userid, UpdateUserNameRequest request){
        User user = userRepository.findById(userid)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ userid));

        user.updateName(request.getName());
        return user;
    }

    // 팔로우
    @Transactional
    public String follow(UpdateFollowRequest request){
        String followerEmail = request.getFollowerEmail();
        String followingEmail = request.getFollowingEmail();
        if(followingEmail == followerEmail){
            return "팔로우할 수 없습니다.";
        }

        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ followerEmail));

        User following = userRepository.findByEmail(followingEmail)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ followingEmail));

        // 이미 팔로우가 되어있는 경우
        if(follower.getFollowingList().contains(followingEmail) && following.getFollowerList().contains(followerEmail)) {
            return "이미 팔로우되어 있습니다.";
        }else if (follower.getFollowingList().contains(followingEmail) && !following.getFollowerList().contains(followerEmail)){
            return "[무결성 에러] 팔로잉 에러";
        }else if (!follower.getFollowingList().contains(followingEmail) && following.getFollowerList().contains(followerEmail)){
            return "[무결성 에러] 팔로워 에러";
        }

        follower.addFollowing(followingEmail);
        following.addFollower(followerEmail);

        return "팔로우가 성공하였습니다.";

    }

    @Transactional
    public String unfollow(UpdateFollowRequest request){
        String followerEmail = request.getFollowerEmail();
        String followingEmail = request.getFollowingEmail();

        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ followerEmail));

        User following = userRepository.findByEmail(followingEmail)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ followingEmail));

        // 팔로우가 되어있지 않은 경우
        if(!follower.getFollowingList().contains(followingEmail) && !following.getFollowerList().contains(followerEmail)) {
            return "팔로우되어 있지 않습니다.";
        }else if (follower.getFollowingList().contains(followingEmail) && !following.getFollowerList().contains(followerEmail)){
            return "[무결성 에러] 팔로잉 에러";
        }else if (!follower.getFollowingList().contains(followingEmail) && following.getFollowerList().contains(followerEmail)){
            return "[무결성 에러] 팔로워 에러";
        }

        follower.deleteFollowing(followingEmail);
        following.deleteFollower(followerEmail);

        return "언팔로우가 성공하였습니다.";

    }

    @Transactional(readOnly = true)
    public List<User> findFollowListByEmail(String followerEmail){
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(followerEmail);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        List<User> followingList = new ArrayList<>();
        for(String followingEmail: user.getFollowingList()){
            User following = userRepository.findByUserEmail(followingEmail);
            followingList.add(following);
        }
        return followingList;
    }
}

