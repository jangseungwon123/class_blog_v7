package com.tenco.blog.board;

import com.tenco.blog._core.errors.exception.Exception403;
import com.tenco.blog._core.errors.exception.Exception404;
import com.tenco.blog.user.SessionUser;
import com.tenco.blog.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)

public class BoardService {

    private final BoardJpaRepository boardJpaRepository;


    /**
     * 게시글 목록 조회 - 페이징 처리
     */
    public List<BoardResponse.MainDTO> list(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Board> boardPage = boardJpaRepository.findAllJoinUser(pageable);
        List<BoardResponse.MainDTO> boardList = new ArrayList<>();
        // 스트림
        // 자료구조에  ---> 반목문을 ---> 객체를 생성.. 재 가공한다면 필터
        // 자료 구조 안에 있는 Stream API
        for (Board board : boardPage.getContent()) {
            BoardResponse.MainDTO mainDTO = new BoardResponse.MainDTO(board);
            boardList.add(mainDTO);
        }

        return boardList;
    }

    // 게시글 상세보기
    public BoardResponse.DetailDTO detail(Long id, SessionUser sessionUser) {
        Board board = boardJpaRepository.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("게시글을 찾을 수 없습니다"));
        return new BoardResponse.DetailDTO(board, sessionUser);
    }

    // 게시글 작성
    @Transactional
    public BoardResponse.SaveDTO save(BoardRequest.SaveDTO saveDTO, SessionUser sessionUser) {
        User user = User.builder()
                .id(sessionUser.getId())
                .username(sessionUser.getUsername())
                .email(sessionUser.getEmail())
                .build();
        Board board = saveDTO.toEntity(user);
        Board savedBoard = boardJpaRepository.save(board);
        return new BoardResponse.SaveDTO(savedBoard);
    }


    /**
     * 게시글 삭제 (권한 체크)
     */
    @Transactional
    public void deleteById(Long id, SessionUser sessionUser) {
        Board board = boardJpaRepository.findById(id).orElseThrow(() ->
             new Exception404("삭제하려는 게시글이 없습니다")
        );
        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }
        boardJpaRepository.deleteById(id);
    }


    /**
     * 게시글 수정 - DTO 변환(권한 체크 포함)
     */
    @Transactional
    public BoardResponse.UpdateDTO update(Long id, BoardRequest.UpdateDTO updateDTO,
                                          SessionUser sessionUser) {
        Board board = boardJpaRepository.findById(id).orElseThrow(() ->
            new Exception404("해당 게시글이 존재하지 않습니다"));

        if (!board.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 게시글만 수정 가능");
        }
        board.Update(updateDTO);
        return new BoardResponse.UpdateDTO(board);
    }


}
