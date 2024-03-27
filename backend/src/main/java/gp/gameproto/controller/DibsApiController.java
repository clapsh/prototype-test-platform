package gp.gameproto.controller;

import gp.gameproto.db.entity.Dibs;
import gp.gameproto.dto.DibsRequest;
import gp.gameproto.dto.GetMyDibsTestResponse;
import gp.gameproto.service.DibsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dib")
public class DibsApiController {
    private final DibsService dibsService;

    @PostMapping()
    public ResponseEntity<?> saveDibs(@RequestBody DibsRequest dibsRequestDTO){
        try {
            dibsService.save(dibsRequestDTO);
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 좋아요를 눌렀습니다.");
        };
        return ResponseEntity.status(HttpStatus.OK)
                .body("좋아요 누르기 성공!");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDibs(@RequestBody DibsRequest dibsRequestDTO){
        String message ="";
        if (dibsService.delete(dibsRequestDTO)){
            message = "삭제 성공!";
        }else{
            message = "삭제 실패!";
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }

    // 유저가 찜한 게임 리스트
    @GetMapping("/games")
    public ResponseEntity<GetMyDibsTestResponse> getUserDibs(@RequestParam("email")String email){
        List<Dibs> dibsList = dibsService.findUserDibs(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetMyDibsTestResponse(dibsList));
    }
}
