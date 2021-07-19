<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
  $("button").click(function(){
    $.ajax({url: "http://apis.data.go.kr/1360000/TourStnInfoService/getTourStnVilageFcst?serviceKey=3vEKgDdI%2Fw1W%2BJljVv43VGttXFT7HDzM2rTDcanBx9oSzjMCvNE687hQYL0%2F7plvDTTrXoqJOqMssYbnqlHw9Q%3D%3D&pageNo=1&numOfRows=10&dataType=XML&CURRENT_DATE=2021071516&HOUR=24&COURSE_ID=1", success: function(result){
      $("#div1").html(result);
    }});
  });
});
</script>
</head>
<body>

<div id="div1" style="width: 100%;border:1px solid blue"></div>
<button>Get External Content</button>
<p>
<iframe width="100%" src="http://apis.data.go.kr/1360000/TourStnInfoService/getTourStnVilageFcst?serviceKey=3vEKgDdI%2Fw1W%2BJljVv43VGttXFT7HDzM2rTDcanBx9oSzjMCvNE687hQYL0%2F7plvDTTrXoqJOqMssYbnqlHw9Q%3D%3D&pageNo=1&numOfRows=10&dataType=XML&CURRENT_DATE=2021071516&HOUR=24&COURSE_ID=1"></iframe>


</body>
</html>





