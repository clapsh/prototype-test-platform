package gp.gameproto.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {
    private String language;
    private String model;
    private int tone;
    private int summaryCount;

    @Builder
    public Option (String language,String model, int tone, int summaryCount ){
        this.language = language;
        this.model = model;
        this.tone = tone;
        this.summaryCount = summaryCount;
    }
}