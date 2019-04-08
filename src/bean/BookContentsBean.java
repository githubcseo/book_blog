package bean;

import java.util.List;

public class BookContentsBean {

	int bookid;
	String bookTitle;
	int bookcontentsId;

	int point;
	String contents;


	List<String> taglist;

	String info;
	
	public String toString(){
		return String.format("%d , %s , %s  " , bookid, bookTitle, contents);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public List<String> getTaglist() {
		return taglist;
	}

	public void setTaglist(List<String> taglist) {
		this.taglist = taglist;
	}


	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public int getBookcontentsId() {
		return bookcontentsId;
	}

	public void setBookcontentsId(int bookcontentsId) {
		this.bookcontentsId = bookcontentsId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
