package GUI;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import five.PIMTodo;
import sql.SQLHelper;

public class TodoGUI extends JFrame{
	private PIMTodo todo = new PIMTodo();
	private boolean isPublic = true;
	private String gowner;
	private SQLHelper sqlHelper;
	public TodoGUI(SQLHelper sqlHelper,String gowner) {
		setName("todo");
		this.gowner = gowner;
		this.sqlHelper = sqlHelper;
		// TODO Auto-generated constructor stub
		JPanel pan1 = new JPanel();
		pan1.setLayout(new GridLayout(5, 2));
		
		JLabel ownerlabel = new JLabel("owner");
		pan1.add(ownerlabel);
		JTextField ownerFiled = new JTextField(10);
		pan1.add(ownerFiled);

			
		
		JLabel prioritylabel = new JLabel("priority");
		pan1.add(prioritylabel);
		JTextField priorityFiled = new JTextField(10);
		pan1.add(priorityFiled);
		
		JLabel datalabel = new JLabel("date,eg(2018/5/28)");
		pan1.add(datalabel);
		JTextField dataFiled = new JTextField(10);
		pan1.add(dataFiled);
		
		JLabel textlabel = new JLabel("text");
		pan1.add(textlabel);
		JTextField textFiled = new JTextField(10);
		pan1.add(textFiled);
		
		JLabel isPublicLable = new JLabel("isPublic");
		pan1.add(isPublicLable);
		JPanel temp = new JPanel();
		JRadioButton trueButton = new JRadioButton("true",true);
		JRadioButton falseButton = new JRadioButton("false");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(trueButton);
		buttonGroup.add(falseButton);
		temp.add(trueButton);
		temp.add(falseButton);
		pan1.add(temp);
		
		JPanel pan2 = new JPanel();
		Button submitButton = new Button("submit");
		Button cancelButton = new Button("cancel");
		pan2.add(submitButton);
		pan2.add(cancelButton);
		
		ownerFiled.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
		        if(arg0.getKeyChar()==KeyEvent.VK_ENTER )   //按回车键执行相应操作; 
		        { 
		        	priorityFiled.requestFocus();
		        } 
			}
		});
		priorityFiled.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
		        if(arg0.getKeyChar()==KeyEvent.VK_ENTER )   //按回车键执行相应操作; 
		        { 
		        	dataFiled.requestFocus();
		        } 
			}
		});
		dataFiled.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
		        if(arg0.getKeyChar()==KeyEvent.VK_ENTER )   //按回车键执行相应操作; 
		        { 
		        	textFiled.requestFocus();
		        } 
			}
		});
		
		trueButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				isPublic = true;
			}
		});
		falseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				isPublic = false;
			}
		});
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String data = dataFiled.getText();
				String owner = ownerFiled.getText();
				String priority = priorityFiled.getText();
				String text = textFiled.getText();
				
				if(!isOk(data) || !isOk(priority) ||!isOk(text)) {
					JOptionPane.showMessageDialog(null, "信息不全");
					return;
				}
				Calendar calendar = fromCalendarString(data);
				if(calendar == null) {
					JOptionPane.showMessageDialog(null, "日期格式错误");
					return;
				}
				if(!isOk(owner) && gowner == null) {
					owner = "nobody";
				}else if(!isOk(owner)) {
					owner = gowner;
				}
				todo.setDate(calendar);
				todo.setPriority(priority);
				todo.setPublic(isPublic);
				todo.setText(text);
				todo.setOwner(owner);
				sqlHelper.add(todo);
				setVisible(false);

			}
		});
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
        //设置主窗口出现在屏幕上的位置
        setBounds(300, 300, 300, 300);
        //设置窗体大小不能被调节
        setResizable(false);
        //设置窗体的默认关闭方式
        //将panel加入frame中
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pan1,BorderLayout.NORTH);
        getContentPane().add(pan2,BorderLayout.SOUTH);
        //frame.pack();//根据窗口里面的布局及组件的preferedSize来确定frame的最佳大小
        
		
	}

	public Calendar fromCalendarString(String temp) {
		// TODO Auto-generated method stub
		
		Calendar calendar= Calendar.getInstance();
		//一直能够输入日期直到日期输入格式正确
		try {
			String[] temps = temp.split("/");
			calendar.set(Integer.parseInt(temps[0]), Integer.parseInt(temps[1]), Integer.parseInt(temps[2]));
		//	System.out.println(temps[2] + " "+temps[1]+" "+temps[0]);
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return calendar;
	}
	public boolean isOk(String s) {
		if(s == null || "".equals(s.trim())) {
			return false;
		}
		return true;
	}
	public void print() {
		System.out.println(todo.getOwner());
		System.out.println(todo.getPriority());
		System.out.println(todo.getText());
		System.out.println(todo.getDate());
		System.out.println(todo.isPublic());
	}
	

}
