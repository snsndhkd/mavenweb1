<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container">
  <h2><button class="btn" onclick="location.href='/index'"><i class="fa fa-home"></i></button>글읽기</h2>
     
    <div class="form-group">
  글번호[${board.idx}] 제목 : ${board.title}<p>
  작성자 : ${board.writeName } 작성일:${board.writeDay} 조회수:${board.readcount}<p>
 </div>  
    <div class="form-group">
      <label for="content">내용</label>
      <textarea class="form-control" disabled id="content" name="content" cols="80%" rows="10" placeholder="글을 입력 하세요">
      ${board.content}
      </textarea>
    </div>
    
    <div class="form-group">
	<button onclick="location.href='/board/board?requestPage=${requestPage}'">목록으로</button>
	<button onclick="location.href='/board/reply?idx=${board.idx}&groupid=${board.groupid}&depth=${board.depth}&reOrder=${board.reOrder}&title=${board.title}&requestPage=${requestPage}'">답글쓰기</button>
	<button onclick="location.href='/board/update?idx=${board.idx}&requestPage=${requestPage}'">글 수정</button>
	<button onclick="location.href='/board/delete?idx=${board.idx}&requestPage=${requestPage}'">글 삭제</button>
    </div>
</div>