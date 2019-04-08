import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookContentsBean;
import code.PrintObject;
import db.DbTest;

public class BookContentsSave extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		res.setCharacterEncoding("UTF-8");

		res.setContentType("text/html;charset = UTF-8");

		String bookid = req.getParameter("bookid").trim();

		String totalcntstr = req.getParameter("totalcnt").trim();
		int totalcnt = Integer.parseInt(totalcntstr);
		String id = "";
		for (int i = 1; i <= totalcnt; i++) {
			String point = req.getParameter("point_" + i).trim();

			String tag1 = "";
			String tag2 = "";
			String tag3 = "";
			String tag4 = "";
			String tag5 = "";

			try {
				tag1 = req.getParameter("tag_" + i + "_1").trim();
				tag2 = req.getParameter("tag_" + i + "_2").trim();
				tag3 = req.getParameter("tag_" + i + "_3").trim();
				tag4 = req.getParameter("tag_" + i + "_4").trim();
				tag5 = req.getParameter("tag_" + i + "_5").trim();
			} catch (Exception e) {
				// TODO: handle exception
			}
			List<String> taglist = new ArrayList<String>();
			if (null != tag1 && tag1.length() > 0)
				taglist.add(tag1);
			if (null != tag2 && tag2.length() > 0)
				taglist.add(tag2);
			if (null != tag3 && tag3.length() > 0)
				taglist.add(tag3);
			if (null != tag4 && tag4.length() > 0)
				taglist.add(tag4);
			if (null != tag5 && tag5.length() > 0)
				taglist.add(tag5);
			id = req.getParameter("book_contents_id_" + i).trim();
			String info = req.getParameter("info_" + i).trim();
			
			if (info.length()> 0 ){
				System.out.println(i + "th info - "  + info);
			}

			try {
				DbTest.getInstance().upBookContents(point, id, info);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				DbTest.getInstance().delBookContentsTags(id);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (String tag : taglist) {
				try {
					DbTest.getInstance().insBookContentsTags(id, tag);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// System.out.println(id + " , " + point + " , " + taglist );
		}
		try {
			DbTest.getInstance().upBook(id);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("~~~~ Hello BookContentsSave ~~~~");

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
		try{
			sc.setAttribute("bookname", retlist.get(0).getBookTitle());
			}catch (Exception e) {
				// TODO: handle exception
			}
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		rd.forward(req, res);

	}

}
