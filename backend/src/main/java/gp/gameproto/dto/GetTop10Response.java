package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetTop10Response {
    private Integer gamesLen;
    private List<Top10Response> gameList;

    public GetTop10Response(List<Test> tests){
        List<Top10Response> top10Games = new ArrayList<>();
        for(Test test: tests){
            Top10Response dto = new Top10Response(test);
            top10Games.add(dto);
        }
        this.gameList = top10Games;
        this.gamesLen = top10Games.size();
    }
}
