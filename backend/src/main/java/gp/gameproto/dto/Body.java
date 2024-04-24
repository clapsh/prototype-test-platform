package gp.gameproto.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Body {
    private Document document;
    private Option option;

    @Builder
    public Body(Document document, Option option){
        this.document = document;
        this.option = option;
    }
}
