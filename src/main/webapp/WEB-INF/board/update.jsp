<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container">
  <h2><button class="btn" onclick="location.href='/index'"><i class="fa fa-home"></i></button>글읽기</h2>
     <form post="method" action="/board/update.do">
    <div class="form-group">
  글번호[${board.idx}] 제목 : <input type="text" name="title" value="${board.title}"><p>
  작성자 : ${board.writeName } 작성일:${board.writeDay} 조회수:${board.readcount}<p>
 </div>  
    <div class="form-group">
      <label for="content">내용</label>
      <textarea class="form-control" id="content" name="content" cols="80%" rows="10" placeholder="글을 입력 하세요">
      ${board.content}
      </textarea>
    </div>
    
    <div class="form-group">
    <input type="submit" value="글 수정">
	<button onclick="location.href='/board/board?reqeustPage=${requestPage}'">수정취소하기</button>
    </div>
    <input type="text" name="idx" value="${board.idx}">
    <input type="text" name="requestPage" value="${requestPage}">
 </form>
</div>