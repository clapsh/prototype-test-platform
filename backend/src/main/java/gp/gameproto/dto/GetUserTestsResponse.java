package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetUserTestsResponse {
    private Integer gamesLen;
    private List<AllUserTestsResponse> gameList;

    public GetUserTestsResponse(List<Test> tests){
        // UserTestsResponse list 로 변환
        List<AllUserTestsResponse> userTests = new ArrayList<>();
        for(Test test: tests){
            AllUserTestsResponse dto = new AllUserTestsResponse(test);
            userTests.add(dto);
        }
        this.gameList = userTests;
        this.gamesLen = userTests.size();
    }
}
