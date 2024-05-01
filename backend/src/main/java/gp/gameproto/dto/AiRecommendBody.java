package gp.gameproto.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiRecommendBody {
    //private Long gameId;
    private String data;

    @Builder
    public AiRecommendBody(String description){
       // this.gameId = gameId;
        this.data = description;
    }
}
