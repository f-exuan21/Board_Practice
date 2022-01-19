package com.hyeon.mvc.board.dao;

import com.hyeon.mvc.board.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface BoardDao {

    public abstract List<BoardVO> list();

    public abstract int delete(BoardVO boardVO);

    public abstract int deleteAll();

    public abstract int update(BoardVO boardVO);

    public abstract void insert(BoardVO boardVO);

    public abstract BoardVO select(int seq);

    public abstract int updateReadCount(int seq);

}
