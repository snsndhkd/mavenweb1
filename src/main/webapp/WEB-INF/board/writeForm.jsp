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
  <h2><button class="btn" onclick="location.href='/index'"><i class="fa fa-home"></i></button>글작성</h2>
  <form name=form action="/board/list" method=post>
    
    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력 하세요">
    </div>
    
    <div class="form-group">
      <label for="title">작성자</label>
      <input type="text" class="form-control" id="write_name" name="writeName" value="${sessionScope.id}" readonly />
    </div>
    
    <div class="form-group">
      <label for="content">글내용</label>
      <textarea class="form-control" id="content" name="content" cols="80%" rows="10" placeholder="글을 입력 하세요">
      </textarea>
    </div>
    
    <button type="button" onclick=dataCheck() class="btn btn-default">입력</button>
  </form>
</div>