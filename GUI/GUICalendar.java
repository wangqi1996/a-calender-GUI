package GUI;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import java.util.Calendar;


import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import five.PIMCollection;
import five.PIMEntity;

import sql.SQLHelper;

//使用JComBox选择日期
public class GUICalendar {
	JFrame frame = new JFrame("Calendar");
	private String refreshFlag = null;
	private Integer[][] days = new Integer[6][7];
	JComboBox<Integer> Jyear;
	JComboBox<Integer> Jmonth;
	JScrollPane jScrollPane;
	int year = 2018;
	int month = 5;
	MyTableModel myTableModel;
	SQLHelper sqlHelper = new SQLHelper();
	private String gowner = null;
	public void initPan1(JPanel pan1) {
		Button prebutton = new Button("pervious month");
		prebutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(month);
				month--;
				System.out.println(month);
				myTableModel.setDays();
				
				Jmonth.setSelectedItem(month);
			}
		});
		pan1.add(prebutton);
		pan1.add(Jyear);
		pan1.add(Jmonth);
		Button nextButton = new Button("next month");
		nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(month);
				month++;
				System.out.println(month);
				myTableModel.setDays();
				Jmonth.setSelectedItem(month);
			}
		});
		pan1.add(nextButton);
	}
	public GUICalendar() {
		initChooseYear();

		JPanel pan1 = new JPanel(); //设置选择日期的按钮
		pan1.setLayout(new FlowLayout());
		initPan1(pan1);
		pan1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JPanel pan2 = new JPanel();//设置周一到周天的显示
		pan2.setLayout(new BorderLayout());
		initTable();
		pan2.add(jScrollPane,BorderLayout.CENTER);
		pan2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//功能按钮
		JPanel pan3 = new JPanel();
		initPan3(pan3);
		
        //设置主窗口出现在屏幕上的位置
        frame.setLocation(300,200);
        frame.setBounds(100, 100, 1000, 700);
        //设置窗体大小不能被调节
        frame.setResizable(false);
        //设置窗体的默认关闭方式
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //将panel加入frame中
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBackground(Color.WHITE);
        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);
        pan3.setBackground(Color.WHITE);
        frame.getContentPane().add(pan1,BorderLayout.NORTH);
        frame.getContentPane().add(pan2,BorderLayout.CENTER);
        frame.getContentPane().add(pan3,BorderLayout.SOUTH);
        //frame.pack();//根据窗口里面的布局及组件的preferedSize来确定frame的最佳大小
        frame.setVisible(true);
	}
	public void initPan3(JPanel pan3) {
		pan3.setLayout(new GridLayout(3,1));
		Button refresh = new Button("refresh");
		Button refreshByOwner = new Button("refresh by Owner");
		Button changeUser = new Button("changeUser");
		JLabel username = new JLabel("all",JLabel.CENTER);
		Button addTodoButton = new Button("add todo");
		
		addTodoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TodoGUI todoGUI = new TodoGUI(sqlHelper,gowner);
				todoGUI.setVisible(true);
			}
		});
		pan3.add(addTodoButton);
		
		Button addAppointmentButton = new Button("add appointment");
		addAppointmentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AppointmentGUI appointmentGUI = new AppointmentGUI(sqlHelper,gowner);
				appointmentGUI.setVisible(true);
			}
		});
		pan3.add(addAppointmentButton);
		
		Button addNoteButton = new Button("add note");
		addNoteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				NoteGUI noteGUI = new NoteGUI(sqlHelper,gowner);
				noteGUI.setVisible(true);
			}
		});
		pan3.add(addNoteButton);
		
		Button addContactButton = new Button("add contact");
		addContactButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ContactGUI contactGUI = new ContactGUI(sqlHelper,gowner);
				contactGUI.setVisible(true);
			}
		});
		pan3.add(addContactButton);
		
		//refresh 的功能在于插入数据之后显示新数据 进行一遍刷新
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				myTableModel.setEntity(null);
			}
		});
		pan3.add(refresh);
		
		//
		refreshByOwner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				while(gowner == null) {
					changeUser.doClick();
				}

				myTableModel.setEntity(gowner);

			}
		});
		pan3.add(refreshByOwner);
		
		Button getAll = new Button("get all");
		getAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PIMCollection pimCollection = sqlHelper.getAll();
				ShowALL showMessage = new ShowALL(pimCollection, "all",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getAll);
		
		Button getAllByOwner = new Button("get all by Owner");
		getAllByOwner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				while(gowner == null) {
					changeUser.doClick();
				}
				PIMCollection pimCollection = sqlHelper.getAllByOwner(gowner);
				ShowALL showMessage = new ShowALL(pimCollection, "all",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getAllByOwner);
		
		Button getNote = new Button("get notes");
		getNote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PIMCollection pimCollection = sqlHelper.getNotes();
				ShowNote showMessage = new ShowNote(pimCollection, "notes",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getNote);
		
		Button getNoteByOwner = new Button("get notes by Owner");
		getNoteByOwner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				while(gowner == null) {
					changeUser.doClick();
				}
				PIMCollection pimCollection = sqlHelper.getNotes(gowner);
				ShowNote showMessage = new ShowNote(pimCollection, "notes",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getNoteByOwner);
		
		Button getContact = new Button("get contacts");
		getContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PIMCollection pimCollection = sqlHelper.getContacts();
				ShowContact showMessage = new ShowContact(pimCollection, "contacts",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getContact);
		
		Button getContactByOwner = new Button("get contacts by Owner");
		getContactByOwner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				while(gowner == null) {
					changeUser.doClick();
				}
				PIMCollection pimCollection = sqlHelper.getContacts(gowner);
				ShowContact showMessage = new ShowContact(pimCollection, "contacts",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getContactByOwner);
		
		Button getTodo = new Button("get todos");
		getTodo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PIMCollection pimCollection = sqlHelper.getTodos();
				ShowTodo showMessage = new ShowTodo(pimCollection, "todos",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getTodo);
		
		Button getTodoByOwner = new Button("get todos by Owner");
		getTodoByOwner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				while(gowner == null) {
					changeUser.doClick();
				}
				PIMCollection pimCollection = sqlHelper.getTodos(gowner);
				ShowTodo showMessage = new ShowTodo(pimCollection, "todos",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getTodoByOwner);
		
		Button getAppointment= new Button("get appointments");
		getAppointment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PIMCollection pimCollection = sqlHelper.getAppointments();
				ShowAppointment showMessage = new ShowAppointment(pimCollection, "appointments",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getAppointment);
		
		Button getAppointmentByOwner = new Button("get appointments by Owner");
		getAppointmentByOwner.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				while(gowner == null) {
					changeUser.doClick();
				}
				PIMCollection pimCollection = sqlHelper.getAppointments(gowner);
				ShowAppointment showMessage = new ShowAppointment(pimCollection, "appointments",sqlHelper);
				showMessage.setVisible(true);
			}
		});
		pan3.add(getAppointmentByOwner);
		
		changeUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String owner = JOptionPane.showInputDialog(null,"please input the owner,没有用户名不输入即可,不指定具体用户请输入all");
				username.setText(owner);
				if(owner == null) {
					owner = "nobody";
					username.setText(owner);
				}else if("all".equals(owner)) {
					owner = null;
				}
				gowner = owner;
				
				
			}
		});
		pan3.add(changeUser,BorderLayout.CENTER);
		
		pan3.add(username);
		
	}
	public int initDays(Integer[][] days) {
		//根据当前日期确定行数,以及该周的第一天为周几
		Calendar calendar = Calendar.getInstance();
		calendar.clear(); //必须得清除
		calendar.set(year, month-1, 1);
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK); //第一天为周几 
		int maxDays = calendar.getActualMaximum(Calendar.DATE);//该月一共有多少天
		calendar.set(Calendar.DATE, maxDays);
		int weeks = calendar.get(Calendar.WEEK_OF_MONTH); //该月有几周
		calendar.clear();//清除很重要
		calendar.set(year,month-2,1);//
		int premaxDays = calendar.getActualMaximum(Calendar.DATE);
		
		
		for(int i = firstDay-2; i >= 0; i--) {
			days[0][i] = premaxDays--;
		}
		int index = firstDay;
		for(int row = 0,i = 1; i <= maxDays ;i++,index++) {
			if(index % 8 == 0) {
				index = 1;
				row++;
			}
			days[row][index-1] = i;
		}
		
		for(int i = 1;index < 8; index++) {
			days[weeks-1][index-1] = i++;
		}
		
		
		return weeks;
	}
	public void initTable() {

		
		myTableModel = new MyTableModel();
		myTableModel.setDays();
		JTable table = new JTable(myTableModel);
		table.setRowHeight(100);//设置行高
		Gender gender = new Gender();
		TableColumnModel tableColumnModel = table.getColumnModel();
		for(int i = 0 ; i < 7; i++) {
			TableColumn tableColumn = tableColumnModel.getColumn(i);
			tableColumn.setCellRenderer(gender);
		}
		//设置监听器
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount() == 2) {
					int row = table.getSelectedRow();
					int col = table.getSelectedColumn();
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month, days[row][col]);
					Date date2 = calendar.getTime();
					final PIMCollection pimEntities;
					if(gowner == null) {
						pimEntities = sqlHelper.getItemsForDate(date2);
					}else {
						pimEntities = sqlHelper.getItemsForDate(date2,gowner);
						
					}
					@SuppressWarnings("deprecation")
					ShowALL showALL = new ShowALL(pimEntities,date2.getYear()+"/"+date2.getMonth()+"/"+date2.getDay(), sqlHelper);
					showALL.setVisible(true);
				}
			}
		});
		//必须把table放入JScrollPane才会出现列名
		jScrollPane = new JScrollPane(table);
		
	}
	public void initChooseYear() {
		Integer[] years = new Integer[1000];
		Integer[] months = new Integer[12];
		for(int i = 0; i < 1000; i++) {
			years[i] = i+1700;
		}
		for(int i = 0; i < 12; i++) {
			months[i] = i+1;
		}
		Jyear = new JComboBox(years);
		Jmonth = new JComboBox(months);
		Jmonth.setSelectedItem(month);
		Jyear.setSelectedItem(year);
		Jyear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				year = (Integer)(((JComboBox<Integer>)e.getSource()).getSelectedItem());
				myTableModel.setDays();
			}
		});
		Jmonth.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				month = (Integer)(((JComboBox<Integer>)e.getSource()).getSelectedItem());
				myTableModel.setDays();
			}
		});
	
	}
	public static void main(String []args) {
		//简单测试
		new GUICalendar();
	}
	//每个单元格的component
	class MyGender extends JPanel{
		String date;
		public MyGender(int value) {
			this.date = value+"";
			setLayout(new BorderLayout());
			
			JPanel pan1 = new JPanel();
			pan1.setBackground(new Color(192, 192, 192));
			pan1.setLayout(new BorderLayout());
			//pan1.setBackground(Color.GREEN);
			JLabel jLabel = new JLabel(this.date);
			
			pan1.add(jLabel,BorderLayout.CENTER);
			
			JPanel pan2 = new JPanel();
			pan2.setLayout(new GridLayout(0,1));
			pan2.setBackground(Color.WHITE);
			//pan2.setBackground(Color.YELLOW);
			Date date2 = new Date(year-1900,month,(Integer)value);
			final PIMCollection pimEntities;
			if(refreshFlag == null) {
				pimEntities = sqlHelper.getItemsForDate(date2);
			}else {
				pimEntities = sqlHelper.getItemsForDate(date2,gowner);
				
			}

			for(PIMEntity pimEntity : pimEntities) {
				JLabel jLabel2 = new JLabel(pimEntity.toString(),SwingConstants.LEFT);
				pan2.add(jLabel2);
			}
			
			add(pan1,BorderLayout.NORTH);
			add(pan2, BorderLayout.CENTER);
		}
		
	}
	//table的渲染器
	class MyTableModel extends AbstractTableModel{
		String[] columnNames = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		private static final long serialVersionUID = 1L;
		
		private int weeks = 0;
		public void setDays() {
			weeks = initDays(days);
			this.fireTableDataChanged();//同时通知JTable数据对象发生更改
		}
		public void setEntity(String owner2) {
			refreshFlag = owner2;
			this.fireTableDataChanged();//同时通知JTable数据对象发生更改
		}
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 7; //列数
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return weeks; //行数
		}
		//设置单元格的内容
		@Override
		public Object getValueAt(int row,int col) {
			// TODO Auto-generated method stub
			return days[row][col];
		}
		//设置表格列名
		@Override
		public String getColumnName(int col) {
			// TODO Auto-generated method stub
			return columnNames[col];
		}
		
	}
	//单元格渲染器
	class Gender implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			// TODO Auto-generated method stub
			int value2 = (Integer)value;
			Component component = null;
			if(row == 0) {
				if(value2 > 20) {
					return null;
				}
			}
			if(row > 3) {
				if(value2 < 10) {
					return null;
				}
			}
			component = new MyGender(value2);
			return component;
		}
	}
}

