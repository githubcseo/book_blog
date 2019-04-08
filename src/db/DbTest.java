package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.BookContentsBean;
import bean.TagBean;

public class DbTest {

	private static DbTest instance;
	private static Connection con;

	// Constructor must be protected or private to perevent creating new object
	private DbTest() throws SQLException, ClassNotFoundException {
		String jdbcstr = "jdbc:mysql://xxx?" + "user=xxx";

		con = DriverManager.getConnection(jdbcstr);

	}

	// could be synchronized
	public static DbTest getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new DbTest();

		}
		return instance;
	}

	
	public static List<TagBean> sel_taglist() throws SQLException {
		List<TagBean> list = new ArrayList<TagBean>();
		PreparedStatement pstmt = null;
		try {
			String queryformat = 
			    "\r\n SELECT tag,c FROM ( " 
			 +  "\r\n SELECT tag,COUNT(*) c  " 
			 +  "\r\n FROM book_contetns_tags " 
			 +  "\r\n GROUP BY tag) " 
			 +  "\r\n a ORDER BY c DESC " 
			 ; 
			String query = String.format(queryformat);
			
			pstmt = con.prepareStatement(query);
			pstmt.executeQuery();

			ResultSet rs1 = pstmt.executeQuery();

			while (rs1.next()) {
				TagBean tag = new TagBean();
				tag.setTag(rs1.getString(1));
				tag.setCount(rs1.getInt(2));

				list.add(tag);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	
	public static List<BookContentsBean> sel_booklist() throws SQLException {
		List<BookContentsBean> list = new ArrayList<BookContentsBean>();
		PreparedStatement pstmt = null;
		try {
			String queryformat = 
			    "SELECT id, title FROM book ORDER BY id DESC"
			 ; 
			String query = String.format(queryformat);
			
			pstmt = con.prepareStatement(query);
			pstmt.executeQuery();

			ResultSet rs1 = pstmt.executeQuery();

			while (rs1.next()) {
				BookContentsBean bcb = new BookContentsBean();
				bcb.setBookid(rs1.getInt(1));
				bcb.setBookTitle(rs1.getString(2));

				list.add(bcb);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	

	public static List<BookContentsBean> sel_booklistWithKeyword(String keyword) throws SQLException {
		List<BookContentsBean> list = new ArrayList<BookContentsBean>();
		PreparedStatement pstmt = null;
		try {
			String queryformat = 
			    "\r\n 			 SELECT b.id,b.title  " 
			 +  "\r\n 			 FROM book_contents bc  " 
			 +  "\r\n 			 JOIN book b ON b.id = bc.book_id  " 
			 +  "\r\n 			 WHERE  bc.content LIKE '%%%s%%' group by b.id " 
			 ; 
			
			System.out.println("sel_booklistWithKeyword111 query- "  + queryformat);
			String query = String.format(queryformat,keyword);
			System.out.println("sel_booklistWithKeyword query- "  + query);
			pstmt = con.prepareStatement(query);
			pstmt.executeQuery();

			ResultSet rs1 = pstmt.executeQuery();

			while (rs1.next()) {
				BookContentsBean bcb = new BookContentsBean();
				bcb.setBookid(rs1.getInt(1));
				bcb.setBookTitle(rs1.getString(2));

				list.add(bcb);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

	public static List<BookContentsBean> sel_tag(String tag) throws SQLException {
		List<BookContentsBean> list = new ArrayList<BookContentsBean>();
		PreparedStatement pstmt = null;
		try {
			String queryformat = 
			    "\r\n SELECT b.id,b.title,bc.id,bc.content, bc.point, bc.info FROM book_contetns_tags bct  " 
			 +  "\r\n JOIN book_contents bc ON bc.id = bct.book_contents_id  " 
			 +  "\r\n JOIN book b ON b.id = bc.book_id " 
			 +  "\r\n WHERE bct.tag = '%s' " 
			 ; 
			
			String query = String.format(queryformat, tag);
			
			pstmt = getContentsList(list, query);

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (pstmt != null)
				pstmt.close();
		}

		return list;
	}
	
	public static List<BookContentsBean> sel_keyword(String tag) throws SQLException {
		List<BookContentsBean> list = new ArrayList<BookContentsBean>();
		PreparedStatement pstmt = null;
		try {
			String queryformat = 
			    "\r\n 			 SELECT b.id,b.title,bc.id,bc.content, bc.point, bc.info  " 
			 +  "\r\n 			 FROM book_contents bc  " 
			 +  "\r\n 			 JOIN book b ON b.id = bc.book_id  " 
			 +  "\r\n 			 WHERE  bc.content LIKE '%%%s%%'; " 
			 ; 
			
			String query = String.format(queryformat, tag);
			
			System.out.println(query);
			pstmt = getContentsList(list, query);

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (pstmt != null)
				pstmt.close();
		}

		return list;
	}
	
	public static String sel_keyword_str(String tag) throws SQLException {
		

			String queryformat = 
			    "\r\n 			 SELECT b.id,b.title,bc.id,bc.content, bc.point, bc.info  " 
			 +  "\r\n 			 FROM book_contents bc  " 
			 +  "\r\n 			 JOIN book b ON b.id = bc.book_id  " 
			 +  "\r\n 			 WHERE  bc.content LIKE '%%%s%%'; " 
			 ; 
			
			String query = String.format(queryformat, tag);
			
			return query;






	}	
	
	public static List<BookContentsBean> sel_book(int bookid) throws SQLException {
		List<BookContentsBean> list = new ArrayList<BookContentsBean>();
		PreparedStatement pstmt = null;
		try {
			String queryformat = "\r\n SELECT b.id,b.title,bc.id,bc.content, bc.point, bc.info FROM book_contents bc  "
					+ "\r\n JOIN book b ON bc.book_id = b.id " + "\r\n WHERE b.id = %d  " + "\r\n ORDER BY bc.content_id ";
			String query = String.format(queryformat, bookid);
			
			pstmt = getContentsList(list, query);

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (pstmt != null)
				pstmt.close();
		}

		return list;
	}

	private static PreparedStatement getContentsList(List<BookContentsBean> list, String query) throws SQLException {
		PreparedStatement pstmt;
		pstmt = con.prepareStatement(query);
		pstmt.executeQuery();

		ResultSet rs1 = pstmt.executeQuery();

		while (rs1.next()) {
			BookContentsBean bcb = new BookContentsBean();
			bcb.setBookid(rs1.getInt(1));
			bcb.setBookTitle(rs1.getString(2));
			bcb.setBookcontentsId(rs1.getInt(3));
			bcb.setContents(rs1.getString(4));
			bcb.setPoint(rs1.getInt(5));
			bcb.setInfo(rs1.getString(6));
			list.add(bcb);

		}

		for (BookContentsBean bcb : list) {
			List<String> taglist = new ArrayList<String>();
			String subqueryStr = "SELECT tag FROM book_contetns_tags WHERE book_contents_id = '%s' ";
			String subquery = String.format(subqueryStr, bcb.getBookcontentsId());
			pstmt = con.prepareStatement(subquery);
			pstmt.executeQuery();

			ResultSet rs2 = pstmt.executeQuery();

			while (rs2.next()) {

				taglist.add(rs2.getString(1));

			}

			for (int i = taglist.size(); i < 5; i++) {

				taglist.add("");
			}

			bcb.setTaglist(taglist);

		}
		return pstmt;
	}

	public static String get_Bookid(String blogurl, String blogid) throws SQLException {

		PreparedStatement pstmt = null;
		try {
			String queryformat = "SELECT id FROM book WHERE blogurl = '%s' AND blogid = '%s'";
			String query = String.format(queryformat, blogurl, blogid);
			
			pstmt = con.prepareStatement(query);
			pstmt.executeQuery();

			ResultSet rs1 = pstmt.executeQuery();

			if (rs1.next()) {
				return rs1.getString(1);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public static List<BookContentsBean> sel_bookContentsList() throws SQLException {
		List<BookContentsBean> list = new ArrayList<BookContentsBean>();
		PreparedStatement pstmt = null;
		try {
			String queryformat = "\r\n SELECT b.id,b.title,bc.id,bc.content, bc.point, bc.info FROM book_contents bc  "
					+ "\r\n JOIN book b ON bc.book_id = b.id " + "\r\n WHERE b.id = %d  " + "\r\n ORDER BY bc.content_id ";
			String query = String.format(queryformat);
			
			pstmt = con.prepareStatement(query);
			pstmt.executeQuery();

			ResultSet rs1 = pstmt.executeQuery();

			while (rs1.next()) {
				BookContentsBean bcb = new BookContentsBean();
				bcb.setBookid(rs1.getInt(1));
				bcb.setBookTitle(rs1.getString(2));
				bcb.setBookcontentsId(rs1.getInt(3));
				bcb.setContents(rs1.getString(4));
				bcb.setPoint(rs1.getInt(5));
				bcb.setInfo(rs1.getString(6));
				list.add(bcb);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	public void insBookContentsTags(String book_contents_id, String tag) throws SQLException {
		PreparedStatement pstmt = null;
		try {

			String querystr = "INSERT INTO book_contetns_tags  (book_contents_id,tag) VALUES('%s','%s');";
			String query = String.format(querystr, book_contents_id, tag);
			pstmt = con.prepareStatement(query);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				pstmt.close();
		}
	}

	public void delBookContentsTags(String book_contents_id) throws SQLException {
		PreparedStatement pstmt = null;
		try {

			String querystr = "delete from book_contetns_tags where book_contents_id = '%s';";
			String query = String.format(querystr, book_contents_id);
			pstmt = con.prepareStatement(query);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				pstmt.close();
		}
	}

	public void upBookContents(String point, String book_contents_id, String info) throws SQLException {
		PreparedStatement pstmt = null;
		try {

			String querystr = "	UPDATE book_contents SET POINT = '%s' , info = '%s' , updttm = NOW() WHERE id = '%s'";
			String query = String.format(querystr, point, info, book_contents_id);
			pstmt = con.prepareStatement(query);

			System.out.println("upBookContents - " + query);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				pstmt.close();
		}
	}

	public void upBook(String book_contents_id) throws SQLException {
		PreparedStatement pstmt = null;
		try {

			String querystr = "UPDATE book b, book_contents bc SET b.updttm = NOW() WHERE b.id = bc.book_id AND bc.id = '%s'";
			String query = String.format(querystr, book_contents_id);
			System.out.println("upBook - " + query);
			pstmt = con.prepareStatement(query);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				pstmt.close();
		}
	}

	public void insBulk(String query, boolean printflag) throws SQLException {
		PreparedStatement pstmt = null;
		try {

			if (printflag) {
				System.out.println();
				System.out.println();
				System.out.println(query);
				System.out.println();
				System.out.println();
			}
			pstmt = con.prepareStatement(query);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				pstmt.close();
		}
	}

	public void insBulk(String query) throws SQLException {
		insBulk(query, true);
	}

}// end of class 