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

public class TagContents extends HttpServlet {

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
		

		


		System.out.println("~~~~ Hello TagContents ~~~~");



		List<BookContentsBean> retlist = null;
		try {
			retlist = DbTest.getInstance().sel_tag(tag);
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
		String url = "/TagContents.vm";

		sc.setAttribute("retlist", retlist);
		sc.setAttribute("tag", tag);
		try{
		sc.setAttribute("bookname", retlist.get(0).getBookTitle());
		}catch (Exception e) {
			// TODO: handle exception
		}
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		rd.forward(req, res);

	}

}
