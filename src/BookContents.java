import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookContentsBean;
import code.PrintObject;
import db.DbTest;

public class BookContents extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset = UTF-8");
		PrintObject debugStringBuffer = new PrintObject();

		String bookid = "";
		try {
			bookid = req.getParameter("bookid").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String bookstr = "";
		try {
			bookstr = req.getParameter("bookstr").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if (bookstr.length()>0){

			try {
				StringTokenizer st = new StringTokenizer(bookstr,"-");
				String blogurl= st.nextToken();
				String blogid= st.nextToken();
				bookid = DbTest.getInstance().get_Bookid(blogurl, blogid);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		System.out.println("~~~~ Hello BookContents ~~~~");

		int beforedays = 50;
		int afterdays = 50;
		int page = 0;
		int pagesize = 5;
		double conditionsdexin = 10;
		double condition_sdvolume = 3;
		double condition_rate_volume = 0.03;

		String error = "";
		PrintObject po = new PrintObject();

		List<BookContentsBean> retlist = null;
		try {
			retlist = DbTest.getInstance().sel_book(Integer.parseInt(bookid));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(po);
		ServletContext sc = this.getServletContext();
		String url = "/BookContents.vm";

		sc.setAttribute("retlist", retlist);
		sc.setAttribute("bookid", bookid);
		try{
		sc.setAttribute("bookname", retlist.get(0).getBookTitle());
		}catch (Exception e) {
			// TODO: handle exception
		}
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		rd.forward(req, res);

	}

}
