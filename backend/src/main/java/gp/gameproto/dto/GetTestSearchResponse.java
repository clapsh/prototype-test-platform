package gp.gameproto.dto;

import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetTestSearchResponse {
    private Integer testsLen;
    private List<TestSearchResponse> testList;

    public GetTestSearchResponse(List<Test> tests){
        List<TestSearchResponse> keywordTests = new ArrayList<>();
        for(Test test: tests){
            TestSearchResponse dto = new TestSearchResponse(test);
            keywordTests.add(dto);
        }
        this.testList = keywordTests;
        this.testsLen = keywordTests.size();
    }
}
