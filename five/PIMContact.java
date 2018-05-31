package five;

import java.util.Scanner;

public class PIMContact extends PIMEntity{
	private String firstName;
	private String lastName;
	private String address;
	private Scanner scanner;
	public String getFirstName() {
		return firstName;
	}
	
	public PIMContact() {
		super();
	}

	public void setFirstName(String fistName) {
		this.firstName = fistName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public PIMContact(String fistName, String lastName, String address,String priority,Scanner scanner,String owner,boolean isPublic) {
		super(priority,scanner,owner,isPublic);
		this.firstName = fistName;
		this.lastName = lastName;
		this.address = address;
		this.scanner = scanner;
	}
	public PIMContact(Scanner scanner) {
		super(scanner);
		this.scanner = scanner;
	}
	@Override
	public void fromString() {
		System.out.println("enter first name: (一个词)");
		this.firstName = scanner.next();
		System.out.println("enter  last name: (一个词)");
		this.lastName = scanner.next();
		System.out.println("enter the email address: (一个词)");
		this.address = scanner.next();
		System.out.println("Enter priority: eg 1,2,3(一个词) ");
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
	
	public Scanner getScanner() {
		return scanner;
	}
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Contact: "+firstName+" "+lastName+" "+address;
	}
	
}
