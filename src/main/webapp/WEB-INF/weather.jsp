<%@page import="org.w3c.dom.NodeList"%>
<%@page import="java.io.StringReader"%>
<%@page import="org.xml.sax.InputSource"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//1.문제 ModelAndView 데이터를 jsp에서 받는 방법
//2.문제 ${data}
String data=(String)request.getAttribute("data");
System.out.println(data);
//data string형태의 xml파일을 분석
DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
DocumentBuilder build=null;
try{
	build=factory.newDocumentBuilder();
	Document doc=build.parse(new InputSource(new StringReader(data)));
	System.out.println(doc.getFirstChild().getNodeName());
	doc.getDocumentElement().normalize();//안정화역할
	NodeList list=doc.getElementsByTagName("tm");
	for(int i=0;i<list.getLength();i++){
		//System.out.println(list.item(i).getNodeName()+":"+list.item(i).getTextContent());
		out.println(list.item(i).getNodeName()+":"+list.item(i).getTextContent()+"<br>");
	}
}catch(Exception e){
	
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- ${data} --%>
<%=data%><br>
<%-- ${context}<br>
${session}<br> --%>
</body>
</html>