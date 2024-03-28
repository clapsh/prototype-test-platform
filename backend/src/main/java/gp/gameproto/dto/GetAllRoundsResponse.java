package gp.gameproto.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GetAllRoundsResponse {
    private Integer roundsCnt;
    private List<Integer> rounds;

    public GetAllRoundsResponse(List<Integer> roundList){
        this.rounds = roundList;
        this.roundsCnt = roundList.size();
    }
}
