package gp.gameproto.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document {
    private String title;
    private String content;

    @Builder
    public Document(String title, String content){
        this.title = title;
        this.content = content;
    }

}