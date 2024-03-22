package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetRecentUserTestsResponse {
    private Integer testsLen;
    private List<RecentUserTestsResponse> tests;
    public GetRecentUserTestsResponse(List<Test> tests){
        List<RecentUserTestsResponse> recentTests = new ArrayList<>();
        for(Test test: tests){
            RecentUserTestsResponse dto = new RecentUserTestsResponse(test);
            recentTests.add(dto);
        }
        this.tests = recentTests;
        this.testsLen = recentTests.size();
    }
}
