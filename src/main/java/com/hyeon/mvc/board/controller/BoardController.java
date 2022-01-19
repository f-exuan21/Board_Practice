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
    public String write(@Valid BoardVO boardVO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/board/write";
        } else {
            boardService.write(boardVO);
            return "redirect:/board/list";
        }
    }

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

}
