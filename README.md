# 간단한 게시판 만들기

## 1. 개발환경
1. Intellij
2. Spring Boot
3. H2 Database (In-Memory 방식)
4. thymeleaf

## 2. 기능
1. 게시판 리스트 /board/list
2. 게시판 등록 /board/write
3. 게시판 보기 /board/read/{seq}
4. 게시판 수정 /board/edit/{seq}
5. 게시판 삭제 /board/delete/{seq}

## 3. 문제점
1. 게시판 보기 페이지에서 boardVO를 세션으로 저장하고 게시판 수정에서 세션에 있는 boardVO의 password와 pwd를 비교함.  
그 과정에서 다른 인터넷 창을 켜서 다른 게시글 수정 페이지를 열고, 다시 원래 수정 페이지를 가진 인터넷 창으로 돌아와서 수정하면 문제가 발생될 것으로 예상됨.  
   - 생각해 본 해결방안
     - 세션을 이용하는 게 아니라 db에서 수정전에 한 번 조회하는 방식