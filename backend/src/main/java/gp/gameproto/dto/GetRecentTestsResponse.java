package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetRecentTestsResponse {
    private Integer gamesLen;
    private List<RecentTestsResponse> gameList;

    public GetRecentTestsResponse(List<Test> tests){
        // RecentTestsResponse list 로 변환
        List<RecentTestsResponse> recentTests = new ArrayList<>();
        for(Test test: tests){
            RecentTestsResponse dto = new RecentTestsResponse(test);
            recentTests.add(dto);
        }
        this.gameList = recentTests;
        this.gamesLen = recentTests.size();
    }
}
