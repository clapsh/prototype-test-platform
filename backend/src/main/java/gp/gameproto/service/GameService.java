package gp.gameproto.service;

import gp.gameproto.db.entity.Game;
import gp.gameproto.db.repository.GameRepository;
import gp.gameproto.dto.AddGameRequest;
import gp.gameproto.dto.UpdateGameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GameService {
    private final GameRepository gameRepository;

    @Transactional
    public String save(AddGameRequest request){

        // 게임이 존재하는 경우
        if(gameRepository.findByName(request.getName()).isPresent()){
            return "게임이 존재합니다.";
        }
        // 게임 저장
        Game game = gameRepository.save(request.toEntity());

        // 게임 반환
        return ""+game.getId();
    }

    @Transactional
    public Game update(UpdateGameRequest request, Long gameId){
        // 게임 찾기
        Game game = gameRepository.findById(gameId)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ gameId));
        // 수정 권한 처리??

        return game.update(request);
    }
}
