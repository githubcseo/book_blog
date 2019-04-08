package code;

import java.util.List;

public class PrintObject {
	StringBuffer sb;

	public String toString(){
		return sb.toString();
	}
	public PrintObject(){
		this.sb = new StringBuffer();
	}
	
	public void append(Object s){
		sb.append("\r\n" + s.toString());
	}
	
	public void appendNewLine(){
		sb.append("\r\n" );
	}
	
	public void println(Object s)
	{
		sb.append("\r\n" + s.toString());
	}
	public void appendList(List list){
		for (Object line : list){
			sb.append("\r\n" + line.toString());
		}
	}	
	
	public String getStr4Web()
	{
		return sb.toString().replace("\r\n", "<br>");
	
	}
	
	public String getStr4Default(){
		return sb.toString();
	}
	
	
}
