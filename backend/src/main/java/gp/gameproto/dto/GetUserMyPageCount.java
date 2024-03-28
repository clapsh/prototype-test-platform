package gp.gameproto.dto;

import gp.gameproto.db.entity.User;
import lombok.Getter;

@Getter
public class GetUserMyPageCount {
    private Long userId;
    private Integer followersCount;
    private Integer gamesCnt;
    private Long reviewsCnt;

    public GetUserMyPageCount(MyPageCount dto){
        this.userId = dto.getUser().getId();
        this.followersCount = dto.getUser().getFollowerList().size();
        this.gamesCnt = dto.getUser().getTests().size();
        this.reviewsCnt = dto.getReviewCnt();
    }
}
