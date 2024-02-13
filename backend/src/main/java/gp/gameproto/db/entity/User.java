package gp.gameproto.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.*;
import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Gender;
import gp.gameproto.db.entity.Follow;
import gp.gameproto.db.entity.Review;
import gp.gameproto.db.entity.Test;
import gp.gameproto.db.entity.UserTest;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "User")
public class User implements UserDetails { // UserDetails를 상속받아 인증 객체로 사용
    @Id
    @Column(name = "user_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String name;

    private String bio; // 자기 소개글

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String nation;

    @Column(nullable = false)
    private Integer age;

    private String imgPath;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    // 이렇게 관리하는 게 맞나..?
    @Column(nullable = false)
    private Category favCategory1;

    @Column(nullable = false)
    private Category favCategory2;

    @Column(nullable = false)
    private Category favCategory3;

    private String status;

    // 객체 생성자
    @Builder
    public User(String email, String pw, String name, String bio, Gender gender, String nation, Integer age, String imgPath,
                LocalDateTime createdAt, Category favCategory1, Category favCategory2, Category favCategory3){
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.bio = bio;
        this.gender = gender;
        this.nation = nation;
        this.age = age;
        this.imgPath = imgPath;
        this.createdAt = createdAt;
        this.favCategory1 = favCategory1;
        this.favCategory2 = favCategory2;
        this.favCategory3 = favCategory3;
    }

    // 연관관계 매핑
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)//유저을 삭제하면 그에 달린 팔로워들도 모두 삭제되도록 cascade = CascadeType.REMOVE를 사용했다
    private List<Follow> followList; //팔로우 목록

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Review> reviewList; // 리뷰 목록

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserTest> userTestList;

    @Override //권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override // 사용자의 id를 반환 (고유한 값)
    public String getUsername(){
        return email;
    }

    @Override // 사용자의 비밀번호를 반환
    public String getPassword(){
        return pw;
    }

    @Override // 계정 만료 여부 반환
    public boolean isAccountNonExpired(){
        // 만료되었는지 확인하는 로직
        return true;
    }


    @Override  // 계정 잠금 여부 반환
    public boolean isAccountNonLocked(){
        // 계정 잠금 되었는지 확인하는 로직
        return true;
    }

    @Override // 패스워드 만료 여부 반환
    public boolean isCredentialsNonExpired(){
        // 페스워드가 만료되었는지 확인하는 로직
        return true;
    }

    @Override // 계정 사용 가능 여부 반환
    public boolean isEnabled(){
        // 계정이 사용 가능한지 확인하는 로직
        return true;
    }

}
