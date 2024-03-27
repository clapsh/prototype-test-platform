package gp.gameproto.dto;

import gp.gameproto.db.entity.Dibs;
import gp.gameproto.db.entity.Test;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetMyDibsTestResponse {
    private Integer dibsCnt;
    private List<MyDibsTestResponse> dibsList;

    public GetMyDibsTestResponse(List<Dibs> dibsList){
        List<MyDibsTestResponse> myDibs = new ArrayList<>();
        for(Dibs dibs:dibsList){
            MyDibsTestResponse dto = new MyDibsTestResponse(dibs.getTest());
            myDibs.add(dto);
        }
        this.dibsList = myDibs;
        this.dibsCnt = myDibs.size();
    }
}
