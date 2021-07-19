<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
function dataCheck(){
	if(document.getElementById("title").value==""){
		alert("제목을 입력해 주세요")
		return;
	}
	if(document.getElementById("content").value==""){
		alert("내용을 입력해 주세요")
		return;
	}
	document.form.submit();
	
}
</script>
</head>
<body>
<div class="container">
  <h2><button class="btn" onclick="location.href='/index'"><i class="fa fa-home"></i></button>댓글 달기</h2>
  <form name=form action="/board/reply.do" method=post>
    
    <div class="form-group">
      <label for="title">제목</label>
      <input value="re:${board.title}" type="text" class="form-control" id="title" name="title" placeholder="제목을 입력 하세요">
    </div>
    
    <div class="form-group">
      <label for="title">작성자</label>
      <input value="${sessionScope.id}" type="text" class="form-control" id="write_name" name="writeName"  readonly />
    </div>
    
    <div class="form-group">
      <label for="content">글내용</label>
      <textarea class="form-control" id="content" name="content" cols="80%" rows="10" placeholder="글을 입력 하세요">
      </textarea>
    </div>
    
    <button type="button" onclick=dataCheck() class="btn btn-default">댓글달기</button>
    <button type="button" onclick="location.href='/board/board?requestPage=${reqeustPage}'" class="btn btn-default">취소</button>
    
    <!-- 댓글 달기 전의 정보(read.jsp)에 대해 숨겨둔 채 정보 전달 -->
    <!-- 처음 글은 idx, groupid가 시퀸스의 nextval을 이용했지만 댓글에서는 groupid에 대한 시퀸 사용 안함-->
    <input type=hidden name="parent_idx" value="${board.idx}"/>
    <input type=hidden name="groupid" value="${board.groupid}"/>
    <input type=hidden name="depth" value="${board.depth}"/>
    <input type=hidden name="reOrder" value="${board.reOrder}"/>
    <input type=hidden name="requestPage" value="${requestPage}"/>
    <input type=hidden name="writeId" value="${sessionScope.id}"/>
  </form>
</div>