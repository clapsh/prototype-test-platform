package gp.gameproto.dto;

import gp.gameproto.db.entity.User;
import lombok.Getter;

@Getter
public class MyPageCount {
    private User user;
    private Long reviewCnt;

    public MyPageCount(User user, Long cnt){
        this.user = user;
        this.reviewCnt = cnt;
    }
}
