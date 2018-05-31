package five;

import java.util.Calendar;
import java.util.Scanner;



public class PIMTodo extends PIMEntity implements DateTest{
	private Calendar date;
	private String text;
	private Scanner scanner;
	
	public PIMTodo() {
		
	}
	public PIMTodo(Scanner scanner) {
		super(scanner);
		this.scanner = PIMEntity.scanner;
	}
	public PIMTodo(Calendar date, String text,String priority,Scanner scanner,String owner,boolean isPublic) {
		super(priority,scanner,owner,isPublic);
		this.date = date;
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public Scanner getScanner() {
		return scanner;
	}
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	@Override
	public void fromString() {
		// TODO Auto-generated method stub
		
		System.out.println("Enter date: eg: 2018/5/28");
		
		String temp = scanner.next();
		//System.out.println(temp);
		date = fromCalendarString(temp);
		System.out.println("Enter text:  (一句话)");
		scanner.nextLine();
		this.text = scanner.nextLine();
		System.out.println("Enter priority:  eg.1,2,3 (一个词)");
		setPriority(scanner.next());
		System.out.println("Enter owner:  (一个词,如果不存在，输入nobody)");
		
		temp = scanner.next();
		if ("nobody".equals(temp)) {
			setOwner(null);
		}else {
			setOwner(temp);
		}
		
		while(true) {
			try {
				System.out.println("Enter isPublic: true/false");
				temp = scanner.next();
				if("true".equals(temp)) {
					setPublic(true);
					break;
				}else if("false".equals(temp)) {
					setPublic(false);
					break;
				}else {
					throw new Exception();
				}
			}catch(Exception e) {
				System.out.println("isPublic格式错误，请重新输入");
			}
		}

	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ToDo: "+text;
	}
	@Override
	public Calendar fromCalendarString(String temp) {
		// TODO Auto-generated method stub
		
		Calendar calendar= Calendar.getInstance();
		//一直能够输入日期直到日期输入格式正确
		while(true) {
			try {
				String[] temps = temp.split("/");
				calendar.set(Integer.parseInt(temps[0]), Integer.parseInt(temps[1]), Integer.parseInt(temps[2]));
			//	System.out.println(temps[2] + " "+temps[1]+" "+temps[0]);
				break;
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("输入格式错误，请重新输入");
				temp = scanner.next();
			}
		}
		return calendar;
	}
	@Override
	public String toCalendarString() {
		// TODO Auto-generated method stub
		return  date.get(Calendar.YEAR)+"/"+ date.get(Calendar.MONTH) +"/"+date.get(Calendar.DAY_OF_MONTH);
	}
	

}
