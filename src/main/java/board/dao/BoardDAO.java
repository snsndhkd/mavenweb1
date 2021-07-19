package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import board.model.BoardVO;
import board.model.PageBoard;

@Repository
public class BoardDAO {
	
	@Autowired
	JdbcConnection jdbc;
	
	public BoardDAO() {
		
	}
	
	public int insert(BoardVO board) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=null;
		try {
			sql="insert into board values(";
			sql+="board_idx_seq.nextval,";
			sql+="?,?,0,"; //title, content
			sql+="board_groupid_seq.nextval,0,0,";
			sql+="0,";
			sql+="?,?,sysdate";//id, name
			sql+=")";
			pstmt=jdbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getWriteId()); //id
			pstmt.setString(4, board.getWriteName()); //name
			
			result=pstmt.executeUpdate();
			
			if(result>0) {
				System.out.println("sql �� �Է� ����");
			}else {
				System.out.println("sql �� �Է� ����");
			}
			pstmt.close();
			//c
			jdbc.ConnectionClose();
			
		}catch(Exception e) { }
		return result;
	}

	public PageBoard list(int requestPage) {
		PageBoard pageboard=null; //������ ������ ����Ʈ�� ��� ��ü
		
		//������ ���� ��������
		                  	//��û ������ ��������(requestPage)
		int articleCount=0;//��ü ���Ǽ�
		int countPerPage=10; //�������� ��� ���� ǥ������ ����(����)
		int totalPage=0;  //��ü ������ =��ü���Ǽ�/���������� ��
		int beginPage=0;  //���� ������-��û�������� �������� ǥ�õ� �������� ���۹�ȣ
		int endPage=0;    //������ ������-��û�������� �������� ǥ�õ� �������� ��������ȣ
		int firstRow=0;   //���� �۹�ȣ -��û�������� �������� ó�� ǥ�õ� �� ��ȣ
		int endRow=0;     //�� �۹�ȣ -��û�������� �������� ó�� ǥ�õ� ������ �� ��ȣ
		List<BoardVO> list=new ArrayList<BoardVO>(); //�������� ���� 10�� �� ����Ʈ
		
		String sql=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
		//�������� ���� ����
		//1.��ü �Խù� �� ���ϱ�(articleCount)
		sql="select count(*) from board";
		pstmt=jdbc.getConnection().prepareStatement(sql);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			articleCount=rs.getInt(1); 
		}else {
			articleCount=0;
		}
		//System.out.println("�Խñۼ�:"+articleCount);
		
		//2.��ü������ ��(totalPage)
		totalPage=articleCount/countPerPage; //�Ҽ�����
		if(articleCount%countPerPage>0) { //���� 35/10=3.5=4page 
			totalPage++;
		}
		//System.out.println("��ü������:"+totalPage);
		
		//3.��û�� �������� ���� ���� �۹�ȣ, �� �۹�ȣ(firstRow, endRow)
		firstRow=(requestPage-1)*countPerPage+1;
		endRow=firstRow+countPerPage-1;
		//System.out.printf("firstRow:%d, endRow:%d\n",firstRow,endRow);
		
		//�߰�����
		//endrow��ȣ�� articleCount���� ū��� endrow�� atricleCount��ȣ�� ����
		
		//4.������������ȣ, �������� ��ȣ(beginPage, endPage)
		//5�������� �������� ���´ٰ� ����
		//�� 3page��û beginPage=��û��������(3)/5=0+1 endpage=beginpage+4
		//�� 7page��û beginPage=��û��������(7)/5=1=(1*5)+1 endpage=beginpage+4=10
		//�� 14page��û beginPage=��û��������(14)/5=2=(2*5)+1 endpage=beginpage+4=15
		
		beginPage=((requestPage-1)/5)*5+1;
		endPage=beginPage+4;
		
	 	if(beginPage<1) {beginPage=1;}
		if(endPage>totalPage) {endPage=totalPage;}
		
		//System.out.printf("beginPage:%d, endPage:%d\n",beginPage,endPage);
		
		//5.�������� �ش��ϴ� ����Ʈ(firstRow, endRow)
		sql="select idx, title, content, readcount, groupid, depth, re_order, isdel, write_id, write_name, write_day from (" + 
				"select rownum rnum, idx, title, content, readcount, groupid, depth, re_order, isdel, write_id, write_name, write_day from (" + 
				"select * from board a order by a.groupid desc, a.re_order asc) where rownum <= ?" + 
				") where rnum >= ?"; 
		pstmt=jdbc.getConnection().prepareStatement(sql);
		pstmt.setInt(1, endRow); //10ū��
		pstmt.setInt(2, firstRow);//1������
		rs=pstmt.executeQuery();
		//6.DB�� ����Ʈ�� board��ü�� ��� ����
		while(rs.next()) {
			//System.out.println("check");
			BoardVO board=new BoardVO();
			board.setIdx(rs.getInt("idx"));
			board.setTitle(rs.getString("title"));
			board.setContent(rs.getString("content"));
			board.setReadcount(rs.getInt("readcount"));
			board.setGroupid(rs.getInt("groupid"));
			board.setDepth(rs.getInt("depth"));
			board.setReOrder(rs.getInt("re_order"));
			board.setIsdel(rs.getInt("isdel"));
			board.setWriteId(rs.getString("write_id"));
			board.setWriteName(rs.getString("write_name"));
			board.setWriteDay(rs.getDate("write_day"));
			list.add(board);
		}
		pageboard=new PageBoard(list, requestPage, 
				articleCount, totalPage, 
				firstRow, endRow, beginPage, endPage);
		//System.out.println(pageboard.getList().toString());
		rs.close();
		pstmt.close();
		jdbc.ConnectionClose();
		
		}catch(Exception e){
			
		}
		return pageboard;
		
		
	}

	public List select() {
		List<BoardVO> list=new ArrayList<BoardVO>();
		String sql="select * from board";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
		pstmt=jdbc.getConnection().prepareStatement(sql);
		rs=pstmt.executeQuery();
		while(rs.next()) {
			BoardVO board=new BoardVO();
			board.setIdx(rs.getInt("idx"));
			board.setTitle(rs.getString("title"));
			board.setContent(rs.getString("content"));
			board.setReadcount(rs.getInt("readcount"));
			board.setGroupid(rs.getInt("groupid"));
			board.setDepth(rs.getInt("depth"));
			board.setReOrder(rs.getInt("re_order"));
			board.setIsdel(rs.getInt("isdel"));
			board.setWriteId(rs.getString("write_id"));
			board.setWriteName(rs.getString("write_name"));
			board.setWriteDay(rs.getDate("write_day"));
			list.add(board);
		}
		}catch(Exception e) {}
		
		return list;
		
	}
	public BoardVO select(int idx) {
		BoardVO board=null;
		String sql="select * from board where idx=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
		pstmt=jdbc.getConnection().prepareStatement(sql);
		pstmt.setInt(1, idx);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			board=new BoardVO();
			board.setIdx(rs.getInt("idx"));
			board.setTitle(rs.getString("title"));
			board.setContent(rs.getString("content"));
			board.setReadcount(rs.getInt("readcount"));
			board.setGroupid(rs.getInt("groupid"));
			board.setDepth(rs.getInt("depth"));
			board.setReOrder(rs.getInt("re_order"));
			board.setIsdel(rs.getInt("isdel"));
			board.setWriteId(rs.getString("write_id"));
			board.setWriteName(rs.getString("write_name"));
			board.setWriteDay(rs.getDate("write_day"));
		}
		rs.close();
		pstmt.close();
		jdbc.ConnectionClose();
		}catch(Exception e) {}
		return board;
	}
	public int delete(int idx) {
		int result=0;
		String sql="delete from board where idx=?";
		PreparedStatement pstmt=null;
		try{
		pstmt=jdbc.getConnection().prepareStatement(sql);
		pstmt.setInt(1, idx);
		result=pstmt.executeUpdate();
		}catch(Exception e) { }
		return result;
	}

	public int update(int idx, String title, String content) {
	int result=0;
	String sql="update board set title=?,content=? where idx=?";
	PreparedStatement pstmt=null;
	try{
	pstmt=jdbc.getConnection().prepareStatement(sql);
	pstmt.setString(1, title);
	pstmt.setString(2, content);
	pstmt.setInt(3, idx);
	result=pstmt.executeUpdate();
	}catch(Exception e) { }
	return result;
	}

	public int replyInsert(BoardVO board) {
		//1��° �θ���� �ִ��� ���� Ȯ��
		if(!parentCheck(board.getIdx())) {
			System.out.println("�θ��Ȯ�ν���!");
			return 0;	
		}
		//2��° ���� �׷� �ٸ� ��ۿ� ���� depth�� 1����
		reply_before_update(board.getGroupid(), board.getReOrder()-1);//-1�� �� Ȯ��
		//3��° �����Է�ó��
		PreparedStatement pstmt=null;
		int result=0;
		String sql=null;
		try {
			sql="insert into board values(";
			sql+="board_idx_seq.nextval,";
			sql+="?,?,0,"; //title, content,��ȸ��
			sql+="?,?,?,";//groupid,depth,reorder
			sql+="0,";
			sql+="?,?,sysdate";//id, name
			sql+=")";
			pstmt=jdbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getGroupid());
			pstmt.setInt(4, board.getDepth());
			pstmt.setInt(5, board.getReOrder());
			pstmt.setString(6, board.getWriteId()); //id
			pstmt.setString(7, board.getWriteName()); //name
			
			result=pstmt.executeUpdate();
			
			if(result>0) {
				System.out.println("sql ��� �Է� ����");
			}else {
				System.out.println("sql ��� �Է� ����");
			}
			pstmt.close();
			jdbc.ConnectionClose();
			
		}catch(Exception e) {e.printStackTrace();}
		return result;
	}

	//�θ���� �ִ��� ���� Ȯ�� �Լ�
	public boolean parentCheck(int idx) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from board where idx=?";
		try {
		pstmt=jdbc.getConnection().prepareStatement(sql);
		pstmt.setInt(1, idx);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			rs.close();
			pstmt.close();
			return true;
		}else {
			rs.close();
			pstmt.close();
			return false;
		}
		}catch(Exception e) {}
		return false;
	}
	//������ depth�� �����ϴ� �Լ�
	public void reply_before_update(int groupid,int reOrder) {
		PreparedStatement pstmt=null;
		String sql="update board set re_order=re_order+1 where groupid=? and re_order>?";
		try {
		pstmt=jdbc.getConnection().prepareStatement(sql);
		pstmt.setInt(1, groupid);
		pstmt.setInt(2, reOrder);
		int result=pstmt.executeUpdate();
		if(result<1) {
			System.out.println("reply�� ������Ʈ�� ������ ����");
		}else {
			System.out.println("reply�� ������Ʈ ����");
		}
		pstmt.close();
		}catch(Exception e) {e.printStackTrace();}
	}

	public int readcountUpdate(int idx) {
		int result=0;
		String sql="update board set readcount=readcount+1 where idx=?";
		PreparedStatement pstmt=null;
		try{
		pstmt=jdbc.getConnection().prepareStatement(sql);
		pstmt.setInt(1, idx);
		result=pstmt.executeUpdate();
		}catch(Exception e) { }
		return result;
		
	}

	public PageBoard searchList(String field, String search, int requestPage) {
	PageBoard pageboard=null; //������ ������ ����Ʈ�� ��� ��ü
		
		//������ ���� ��������
		                  	//��û ������ ��������(requestPage)
		int articleCount=0;//��ü ���Ǽ�
		int countPerPage=10; //�������� ��� ���� ǥ������ ����(����)
		int totalPage=0;  //��ü ������ =��ü���Ǽ�/���������� ��
		int beginPage=0;  //���� ������-��û�������� �������� ǥ�õ� �������� ���۹�ȣ
		int endPage=0;    //������ ������-��û�������� �������� ǥ�õ� �������� ��������ȣ
		int firstRow=0;   //���� �۹�ȣ -��û�������� �������� ó�� ǥ�õ� �� ��ȣ
		int endRow=0;     //�� �۹�ȣ -��û�������� �������� ó�� ǥ�õ� ������ �� ��ȣ
		List<BoardVO> list=new ArrayList<BoardVO>(); //�������� ���� 10�� �� ����Ʈ
		
		String sql=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
		//�������� ���� ����
		//1.��ü �Խù� �� ���ϱ�(articleCount)
		sql="select count(*) from board where "+field+" like '%"+search+"%'";
		pstmt=jdbc.getConnection().prepareStatement(sql);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			articleCount=rs.getInt(1); 
		}else {
			articleCount=0;
		}
		//System.out.println("�˻� �Խñۼ�:"+articleCount);
		
		//2.��ü������ ��(totalPage)
		totalPage=articleCount/countPerPage; //�Ҽ�����
		if(articleCount%countPerPage>0) { //���� 35/10=3.5=4page 
			totalPage++;
		}
		//System.out.println("��ü������:"+totalPage);
		
		//3.��û�� �������� ���� ���� �۹�ȣ, �� �۹�ȣ(firstRow, endRow)
		firstRow=(requestPage-1)*countPerPage+1;
		endRow=firstRow+countPerPage-1;
		//System.out.printf("firstRow:%d, endRow:%d\n",firstRow,endRow);
		
		//�߰�����
		//endrow��ȣ�� articleCount���� ū��� endrow�� atricleCount��ȣ�� ����
		
		//4.������������ȣ, �������� ��ȣ(beginPage, endPage)
		//5�������� �������� ���´ٰ� ����
		//�� 3page��û beginPage=��û��������(3)/5=0+1 endpage=beginpage+4
		//�� 7page��û beginPage=��û��������(7)/5=1=(1*5)+1 endpage=beginpage+4=10
		//�� 14page��û beginPage=��û��������(14)/5=2=(2*5)+1 endpage=beginpage+4=15
		
		beginPage=(requestPage/5)*5+1;
		endPage=beginPage+4;
		
	 	if(beginPage<1) {beginPage=1;}
		if(endPage>totalPage) {endPage=totalPage;}
		
		//System.out.printf("beginPage:%d, endPage:%d\n",beginPage,endPage);
		
		//5.�������� �ش��ϴ� ����Ʈ(firstRow, endRow)
		sql="select idx, title, content, readcount, groupid, depth, re_order, isdel, write_id, write_name, write_day from (" + 
				"select rownum rnum, idx, title, content, readcount, groupid, depth, re_order, isdel, write_id, write_name, write_day from (" + 
				"select * from board a  where "+field+" like '%"+search+"%' order by a.groupid desc, a.re_order asc) where rownum <= ?" + 
				") where rnum >= ?"; 
		pstmt=jdbc.getConnection().prepareStatement(sql);
		pstmt.setInt(1, endRow); //10ū��
		pstmt.setInt(2, firstRow);//1������
		rs=pstmt.executeQuery();
		//6.DB�� ����Ʈ�� board��ü�� ��� ����
		while(rs.next()) {
			//System.out.println("check");
			BoardVO board=new BoardVO();
			board.setIdx(rs.getInt("idx"));
			board.setTitle(rs.getString("title"));
			board.setContent(rs.getString("content"));
			board.setReadcount(rs.getInt("readcount"));
			board.setGroupid(rs.getInt("groupid"));
			board.setDepth(rs.getInt("depth"));
			board.setReOrder(rs.getInt("re_order"));
			board.setIsdel(rs.getInt("isdel"));
			board.setWriteId(rs.getString("write_id"));
			board.setWriteName(rs.getString("write_name"));
			board.setWriteDay(rs.getDate("write_day"));
			list.add(board);
		}
		pageboard=new PageBoard(list, requestPage, 
				articleCount, totalPage, 
				firstRow, endRow, beginPage, endPage);
		//System.out.println(pageboard.getList().toString());
		rs.close();
		pstmt.close();
		jdbc.ConnectionClose();
		
		}catch(Exception e){
			
		}
		return pageboard;
		
	}
	}
