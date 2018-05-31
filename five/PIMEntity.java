package five;


import java.util.Scanner;

public abstract class PIMEntity {
	private String Priority;
	public static Scanner scanner; //�������ӿڱ����ɴ����ഫ��
	private String owner;
	private boolean isPublic;
	// default constructor sets priority to "normal"
	//�����ʱ���øú���
	public PIMEntity(Scanner scanner) {
		// TODO Auto-generated constructor stub
		PIMEntity.scanner = scanner;
		this.Priority = "normal";
	}
	// priority can be established via this constructor.
	
	public String getPriority() {
		return Priority;
	}
	//�����ݿ��ȡ�øú���
	public PIMEntity() {
		super();
	}

	public PIMEntity(String priority, Scanner scanner,String owner, boolean isPublic) {
		super();
		Priority = priority;
		this.owner = owner;
		this.isPublic = isPublic;
	}
	public void setPriority(String priority) {
		Priority = priority;
	}
	public void setScanner(Scanner scanner) {
		PIMEntity.scanner = scanner;
	}
	
    public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public Scanner getScanner() {
		return scanner;
	}
	abstract public void fromString() ;
    abstract public String toString();
}
