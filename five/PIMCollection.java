package five;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Scanner;



public class PIMCollection extends ArrayList<PIMEntity>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Scanner scanner = new Scanner(System.in);
	public Collection<PIMNote> getNotes(){
		ArrayList<PIMNote> pimNotes = new ArrayList<>();
		for(PIMEntity entity:this) {
			if(entity instanceof PIMNote) {
				pimNotes.add((PIMNote)entity);
			}
		}
		return pimNotes;
	}
	
	public Collection<PIMTodo> getTodos(){
		ArrayList<PIMTodo> pimTodos = new ArrayList<>();
		for(PIMEntity entity:this) {
			if(entity instanceof PIMTodo) {
				pimTodos.add((PIMTodo)entity);
			}
		}
		return pimTodos;
	}
	
	public Collection<PIMAppointment> getAppointments(){
		ArrayList<PIMAppointment> pimAppointments = new ArrayList<>();
		for(PIMEntity entity:this) {
			if(entity instanceof PIMAppointment) {
				pimAppointments.add((PIMAppointment)entity);
			}
		}
		return pimAppointments;
	}
	
	public Collection<PIMContact> getContacts(){
		ArrayList<PIMContact> pimContacts = new ArrayList<>();
		for(PIMEntity entity:this) {
			if(entity instanceof PIMContact) {
				pimContacts.add((PIMContact)entity);
			}
		}
		return pimContacts;
	}
	
	public Collection<PIMEntity> getItemForDate(Date d){
		ArrayList<PIMEntity> dates = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		String s = toCalendarString(calendar);
		for(PIMEntity entity:this) {
			if(entity instanceof PIMAppointment) {
				
				PIMAppointment temp = (PIMAppointment)entity;
				if(s.equals(toCalendarString(temp.getDate()))) {
					dates.add(temp);
				}
			}
			if(entity instanceof PIMTodo) {
				PIMTodo temp = (PIMTodo)entity;
				if(s.equals(toCalendarString(temp.getDate()))) {
					dates.add(temp);
				}
			}
		}
		return dates;
	}
	public static PIMEntity create() {
		System.out.println("Enter an item type ( todo, note, contact or appointment )");
		String temp = scanner.next();
		PIMEntity entity = null;
		if("todo".equals(temp)) {
			entity = new PIMTodo(scanner);
			entity.fromString();
		}else if ("note".equals(temp)) {
			entity = new PIMNote(scanner);
			entity.fromString();
		}else if("contact".equals(temp)) {
			entity = new PIMContact(scanner);
			entity.fromString();
		}else if("appointment".equals(temp)) {
			entity = new PIMAppointment(scanner);
			entity.fromString();
		}else {
			System.out.println("请输入正确的类别");
			create();
		}
		return entity;
	}
	//相当于增加了一种比较方式而已
	public String toCalendarString(Calendar date) {
		// TODO Auto-generated method stub
		return date.get(Calendar.YEAR) + "/" + date.get(Calendar.MONTH) + "/"+date.get(Calendar.DAY_OF_MONTH) ;
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String []args) {
		
		PIMCollection pimCollection = new PIMCollection();
		PIMEntity temp = create();
		pimCollection.add(temp);
		temp = create();
		pimCollection.add(temp);
		System.out.println(pimCollection.getTodos());
		temp = create();
		pimCollection.add(temp);
		temp = create();
		pimCollection.add(temp);
		System.out.println(pimCollection.getAppointments());
		temp = create();
		pimCollection.add(temp);
		temp = create();
		pimCollection.add(temp);
		System.out.println(pimCollection.getContacts());
		temp = create();
		pimCollection.add(temp);
		temp = create();
		pimCollection.add(temp);
		
		
		System.out.println(pimCollection.getItemForDate(new Date(2018-1900, 5, 28)));//代表日期 2018.5.28
		
	}
	
	
}
