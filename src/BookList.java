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
import javax.swing.text.html.HTML.Tag;

import bean.BookContentsBean;
import bean.TagBean;
import code.PrintObject;
import db.DbTest;

public class BookList extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset = UTF-8");
		PrintObject debugStringBuffer = new PrintObject();

		String tag = "";
		try {
			tag = req.getParameter("tag").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		

		


		System.out.println("~~~~ Hello TagContents ~~~~" + tag + " , " + tag.length());




		List<BookContentsBean> retlist = null;
		

		
		
		try {
			retlist = DbTest.getInstance().sel_booklist();
			
			
			if (tag.length()>0){
				System.out.println("with keyword");
				retlist = DbTest.getInstance().sel_booklistWithKeyword(tag);
			} else{
				System.out.println("without keyword");
				retlist = DbTest.getInstance().sel_booklist();
			}
			
			
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
		String url = "/BookList.vm";

		sc.setAttribute("retlist", retlist);
		

		RequestDispatcher rd = sc.getRequestDispatcher(url);
		rd.forward(req, res);

	}

}
