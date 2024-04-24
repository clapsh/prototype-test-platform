package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetAIRecommendResponse {
    private Integer gamesLen;
    private List<AiTop8Response> gameList;

    public GetAIRecommendResponse(List<Test> tests){
        List<AiTop8Response> aiTop8Games = new ArrayList<>();
        for(Test test: tests){
            AiTop8Response dto = new AiTop8Response(test);
            aiTop8Games.add(dto);
        }
        this.gameList = aiTop8Games;
        this.gamesLen = aiTop8Games.size();
    }
}
