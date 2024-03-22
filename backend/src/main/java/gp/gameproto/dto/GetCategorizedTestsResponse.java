package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class GetCategorizedTestsResponse {
    private Integer gamesLen;
    private List<CategorizedTestsResponse> gameList;

    public GetCategorizedTestsResponse(List<Test> tests){
        // CategorizedGamesResponse list 로 변환
        List<CategorizedTestsResponse> recentTests = new ArrayList<>();
        for(Test test: tests){
            CategorizedTestsResponse dto = new CategorizedTestsResponse(test);
            recentTests.add(dto);
        }
        this.gameList = recentTests;
        this.gamesLen = recentTests.size();
    }
}
