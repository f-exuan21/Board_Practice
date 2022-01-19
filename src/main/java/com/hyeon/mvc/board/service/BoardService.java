package com.hyeon.mvc.board.service;

import com.hyeon.mvc.board.domain.BoardVO;

import java.util.List;

public interface BoardService {
    public abstract List<BoardVO> list();

    public abstract int delete(BoardVO boardVO);

    public abstract int edit(BoardVO boardVO);

    public abstract void write(BoardVO boardVO);

    public abstract BoardVO read(int seq);
}
