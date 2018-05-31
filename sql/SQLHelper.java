package sql;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import five.PIMAppointment;
import five.PIMCollection;
import five.PIMContact;
import five.PIMEntity;
import five.PIMNote;
import five.PIMTodo;


public class SQLHelper implements RemotePIMCollection{
	final String driver = "com.mysql.jdbc.Driver";
	private String DBname = "CalenderTest"; //数据库名
	private String URL = "jdbc:mysql://localhost:3306/" + DBname+"?useSSL=true";
	private String username = "";//更改为自己的mysql用户名
	private String userpwd = "";//更爱为自己的password
	private Statement statement;
 	public SQLHelper() {
		// TODO Auto-generated constructor stub
		try {
			//注册jdbc驱动
			Class.forName(driver);
		}catch(Exception e) {
			System.out.println("can't load Driver");
			return;
		}
		try {
			//链接数据库
			Connection connection = DriverManager.getConnection(URL,username,userpwd);
			statement = connection.createStatement();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("获取 connection失败");
			return;
		}
		//构造四个表 
		String createTableTodo = "create table  if not exists todo("
				+ "owner char(50),"
				+ "isPublic char(10),"
				+ "priority char(50),"
				+ "data char(50),"
				+ "text char(50));";
		String createTableNote = "create table  if not exists note("
				+ "owner varchar(50),"
				+ "isPublic char(10),"
				+ "priority varchar(50),"
				+ "text varchar(50));";
		String createTableContact = "create table  if not exists contact("
				+ "owner varchar(50),"
				+ "isPublic char(10),"
				+ "priority varchar(50),"
				+ "firstname varchar(50),"
				+ "lastname varchar(50),"
				+ "address varchar(50)"
				+ ");";
		String createTableAppointment = "create table  if not exists appointment("
				+ "owner varchar(50),"
				+ "isPublic char(10),"
				+ "priority varchar(50),"
				+ "data varchar(50),"
				+ "text varchar(50));";
		try {
			statement.execute(createTableTodo);
			statement.execute(createTableNote);
			statement.execute(createTableAppointment);
			statement.execute(createTableContact);
		}catch(Exception exception) {
			System.out.println("创建表失败");
		}
	}


	@Override
	public PIMCollection getNotes() {
		// TODO Auto-generated method stub
		String sql = "select * from note where isPublic = 'true';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMNote note = new PIMNote();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				note.setOwner(owner);
				note.setPriority(priority);
				note.setPublic(isPublic);
				note.setText(text);
				collection.add(note);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getNotes(String owner2) {
		// TODO Auto-generated method stub
		String sql = "select * from note where owner = '"+owner2+"';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMNote note = new PIMNote();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");

				note.setOwner(owner);
				note.setPriority(priority);
				note.setPublic(isPublic);
				note.setText(text);
				collection.add(note);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	//存入数据库的字符串为 2018/5/28
	@Override
	public PIMCollection getTodos() {
		// TODO Auto-generated method stub
		String sql = "select * from todo where isPublic = 'true';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMTodo todo = new PIMTodo();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				String data = resultSet.getString("data");
				todo.setOwner(owner);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setDate(todo.fromCalendarString(data)); 
				collection.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getTodos(String owner2) {
		// TODO Auto-generated method stub
		String sql = "select * from todo where owner = '"+owner2+"';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMTodo todo = new PIMTodo();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				String data = resultSet.getString("data");
				todo.setOwner(owner);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setDate(todo.fromCalendarString(data)); 
				collection.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getAppointments() {
		// TODO Auto-generated method stub
		String sql = "select * from appointment  where isPublic ='true';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMAppointment todo = new PIMAppointment();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				String data = resultSet.getString("data");
				todo.setOwner(owner);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setDate(todo.fromCalendarString(data)); 
				collection.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getAppointments(String owner2) {
		// TODO Auto-generated method stub
		String sql = "select * from appointment where owner = '"+owner2+"';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMAppointment todo = new PIMAppointment();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				String data = resultSet.getString("data");
				todo.setOwner(owner);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setDate(todo.fromCalendarString(data)); 
				collection.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getContacts() {
		// TODO Auto-generated method stub
		String sql = "select * from contact  where isPublic = 'true';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMContact contact = new PIMContact();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String address = resultSet.getString("address");
				contact.setOwner(owner);
				contact.setPriority(priority);
				contact.setPublic(isPublic);
				contact.setFirstName(firstname);
				contact.setLastName(lastname);
				contact.setAddress(address);
				collection.add(contact);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getContacts(String owner2) {
		// TODO Auto-generated method stub
		String sql = "select * from contact where owner = '"+owner2+"';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMContact contact = new PIMContact();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String address = resultSet.getString("address");
				contact.setOwner(owner);
				contact.setPriority(priority);
				contact.setPublic(isPublic);
				contact.setFirstName(firstname);
				contact.setLastName(lastname);
				contact.setAddress(address);
				collection.add(contact);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getItemsForDate(Date d) {
		// TODO Auto-generated method stub
		PIMCollection pimCollection = new PIMCollection();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		String data2 =  pimCollection.toCalendarString(calendar);
		String sql = "select * from appointment where data = '"+data2+"';";

		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMAppointment todo = new PIMAppointment();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				String data = resultSet.getString("data");
				todo.setOwner(owner);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setDate(todo.fromCalendarString(data)); 
				collection.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		sql = "select * from todo where data = '"+data2+"';";
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMTodo todo = new PIMTodo();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				String data = resultSet.getString("data");
				todo.setOwner(owner);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setDate(todo.fromCalendarString(data)); 
				collection.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getItemsForDate(Date d, String owner2) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		PIMCollection pimCollection = new PIMCollection();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		String data2 =  pimCollection.toCalendarString(calendar);
		String sql = "select * from appointment where data = '"+data2+"' and owner = '"+owner2+"';";
		PIMCollection collection = new PIMCollection();
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMAppointment todo = new PIMAppointment();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				String data = resultSet.getString("data");
				todo.setOwner(owner);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setDate(todo.fromCalendarString(data)); 
				collection.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		sql = "select * from todo where data = '"+data2+"' and owner = '"+owner2+"';";
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				PIMTodo todo = new PIMTodo();
				String owner = resultSet.getString("owner");
				Boolean isPublic = Boolean.parseBoolean(resultSet.getString("isPublic"));
				String priority = resultSet.getString("priority");
				String text = resultSet.getString("text");
				String data = resultSet.getString("data");
				todo.setOwner(owner);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setDate(todo.fromCalendarString(data)); 
				collection.add(todo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collection;
	}
	@Override
	public PIMCollection getAll() {
		// TODO Auto-generated method stub
		PIMCollection pimCollection = new PIMCollection();
		pimCollection.addAll(getContacts());
		pimCollection.addAll(getAppointments());
		pimCollection.addAll(getTodos());
		pimCollection.addAll(getNotes());
		return pimCollection;
	}
	@Override
	public PIMCollection getAllByOwner(String owner) {
		// TODO Auto-generated method stub
		PIMCollection pimCollection = new PIMCollection();
		pimCollection.addAll(getContacts(owner));
		pimCollection.addAll(getAppointments(owner));
		pimCollection.addAll(getTodos(owner));
		pimCollection.addAll(getNotes(owner));
		return pimCollection;
	}
	@Override
	public boolean add(PIMEntity pimEntity) {
		// TODO Auto-generated method stub
		String sql = null;
		if(pimEntity instanceof PIMContact) {
			PIMContact contact = (PIMContact)pimEntity;
			sql = "insert into contact values('"+contact.getOwner()+"','"+isPublicString(contact.isPublic())+"','"+contact.getPriority()+"','"
					+ contact.getFirstName()+"','"+contact.getLastName()+"','"+contact.getAddress()+"');";
		}else if(pimEntity instanceof PIMTodo) {
			PIMTodo todo = (PIMTodo)pimEntity;
			sql = "insert into todo values('"+todo.getOwner()+"','"+isPublicString(todo.isPublic())+"','"+todo.getPriority()+"','"
					+ toCalendarString(todo.getDate())+"','"+todo.getText()+"');";
			
		}else if(pimEntity instanceof PIMNote) {
			PIMNote note = (PIMNote)pimEntity;
			sql = "insert into note values('"+note.getOwner()+"','"+isPublicString(note.isPublic())+"','"+note.getPriority()+"','"
					+note.getText()+"');";
		}else if(pimEntity instanceof PIMAppointment) {
			PIMAppointment appointment = (PIMAppointment)pimEntity;
			sql = "insert into appointment values('"+appointment.getOwner()+"','"+isPublicString(appointment.isPublic())+"','"+appointment.getPriority()+"','"
					+toCalendarString(appointment.getDate())+"','"+appointment.getText()+"');";
		}else {
			return false;
		}
		try {
			System.out.println(sql);
			statement.execute(sql);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public String toCalendarString(Calendar date) {
		// TODO Auto-generated method stub
		return date.get(Calendar.YEAR) + "/" + date.get(Calendar.MONTH) + "/"+date.get(Calendar.DAY_OF_MONTH) ;
	}
	public String isPublicString(boolean b) {
		if(b) {
			return "true";
		}else {
			return "false";
		}
	}
	public void update(String sql) {
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
