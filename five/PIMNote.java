package five;


import java.util.Scanner;

public class PIMNote extends PIMEntity{
	private String text;
	private Scanner scanner;
	public String getText() {
		return text;
	}
	
	public PIMNote() {
		super();
	}

	public void setText(String text) {
		this.text = text;
	}

	public PIMNote(Scanner scanner) {
		super(scanner);
		this.scanner = PIMEntity.scanner;
	}

	public PIMNote(String text,String priority,Scanner scanner,String owner,boolean isPublic) {
		super(priority,scanner,owner,isPublic);
		this.text = text;
		this.scanner = PIMEntity.scanner;
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
		System.out.println("Enter text: (一句话)");
		scanner.nextLine();
		this.text = scanner.nextLine();
		System.out.println("Enter priority: eg 1,2,3 (一个词)");
		setPriority(scanner.next());
		System.out.println("Enter owner:  (一个词,如果不存在，输入nobody)");
		
		String temp = scanner.next();
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
		return "Note: " +  text;
	}
	
	
	

}
