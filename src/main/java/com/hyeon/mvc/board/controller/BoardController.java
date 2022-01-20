package com.hyeon.mvc.board.controller;

import com.hyeon.mvc.board.domain.BoardVO;
import com.hyeon.mvc.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@SessionAttributes("boardVO")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(value="/board/list")
    public String list(Model model) {
        model.addAttribute("boardList", boardService.list());
        return "/board/list";
    }

    @RequestMapping(value="/board/read/{seq}") //{seq} : 경로변수
    public String read(Model model, @PathVariable int seq) { //경로변수를 사용하기 위해 @PathVariable 어노테이션을 지정 : 경로변수의 값이 자동으로 바인딩됨
        model.addAttribute("boardVO", boardService.read(seq));
        return "/board/read";
    }

    @RequestMapping(value="/board/write", method = RequestMethod.GET)
    public String write(Model model) {
        model.addAttribute("boardVO", new BoardVO());
        return "/board/write";
    }

    @RequestMapping(value="/board/write", method = RequestMethod.POST)
    public String write(@Valid BoardVO boardVO, BindingResult bindingResult, SessionStatus sessionStatus) {
        if(bindingResult.hasErrors()) {
            return "/board/write";
        } else {
            boardService.write(boardVO);
            sessionStatus.setComplete(); // board/write GET 에서 생성된 세션을 제거
            return "redirect:/board/list";
        }
    }

    /*
    * 게시판 구조 : 읽기 화면 -> 수정 화면
    * 읽기화면에서 생성한 BoardVO 객체를 이미 세션에 가지고 있는 상태이기 때문에
    * 같은 객체를 추가하는 형국이 된다.
    * => 세션으로부터 저장되어 있는 BoardVO를 받아오도록 코드 수정
    * => But, 같은 세션을 공유하는 2개의 브라우저에서 비밀번호가 같은 2개의 글을 동시에 수정하면 문제가 발생해 처리하지 않음
    */
    @RequestMapping(value="/board/edit/{seq}", method = RequestMethod.GET)
    public String edit(@PathVariable int seq, Model model) {
        BoardVO boardVO = boardService.read(seq);
        model.addAttribute("boardVO", boardVO);
        return "/board/edit";
    }

    @RequestMapping(value="/board/edit/{seq}", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute BoardVO boardVO, @PathVariable int seq, BindingResult bindingResult, int pwd, SessionStatus sessionStatus, Model model) {
        if (bindingResult.hasErrors()) {
            return "/board/edit";
        } else {
            if (boardVO.getPassword() == pwd) {
                boardService.edit(boardVO);
                sessionStatus.setComplete();
                return "redirect:/board/list";
            }
        }
        model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
        return "/board/edit";
    }

    @RequestMapping(value="/board/delete/{seq}", method = RequestMethod.GET)
    public String delete(@PathVariable int seq, Model model) {
        //model.addAttribute("seq", seq);
        return "/board/delete";
    }

    @RequestMapping(value="/board/delete", method=RequestMethod.POST)
    public String delete(int seq, int pwd, Model model) {
        int rowCount;

        BoardVO boardVO = new BoardVO();
        boardVO.setPassword(pwd);
        boardVO.setSeq(seq);

        rowCount = boardService.delete(boardVO);

        if(rowCount == 0) {
            model.addAttribute("seq", seq);
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "/board/delete";
        } else {
            return "redirect:/board/list";
        }
    }
}
