package GUI;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import five.PIMAppointment;
import five.PIMCollection;
import five.PIMContact;
import five.PIMNote;
import five.PIMTodo;
import sql.SQLHelper;

public class ShowALL extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;
	private PIMCollection pimCollection;
	private MyTableModel model;
	private SQLHelper sqlHelper;
	private String[][] text ;
	
	public ShowALL(PIMCollection pimCollection,String msg,SQLHelper sqlHelper){
		super();
		setName(msg);
		this.sqlHelper = sqlHelper;
		this.pimCollection = pimCollection;
		model = new MyTableModel();
		model.setDays();
		
		table = new JTable(model);
		table.setRowHeight(50);

		JScrollPane pan1 = new JScrollPane();
		pan1.setViewportView(table);
		
        //设置主窗口出现在屏幕上的位置
        setBounds(100, 100, 700, 700);
        //设置窗体大小不能被调节
        setResizable(false);
        //设置窗体的默认关闭方式
        //将panel加入frame中
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pan1,BorderLayout.CENTER);
        //pack();//根据窗口里面的布局及组件的preferedSize来确定frame的最佳大小
		
	}
	


	
	//table的渲染器
	class MyTableModel extends AbstractTableModel{
		String[] columnNames = {"type","owner","isPublic","priority","firstname","lastname","address","data","text"};
		private static final long serialVersionUID = 1L;
		
		
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}
		public void setDays() {
			text = new String[pimCollection.size()][columnNames.length];
			for(int i = 0; i < pimCollection.size(); i++) {
				if(pimCollection.get(i) instanceof PIMContact) {
					PIMContact todo = (PIMContact)(pimCollection.get(i));
					text[i][0] = "contact";
					text[i][1] = todo.getOwner();
					text[i][2] = String.valueOf(todo.isPublic());
					text[i][3] = todo.getPriority();
					text[i][4] = todo.getFirstName();
					text[i][5] = todo.getLastName();
					text[i][6] = todo.getAddress();
				}else if(pimCollection.get(i) instanceof PIMTodo) {
					PIMTodo todo = (PIMTodo)(pimCollection.get(i));
					text[i][0] = "todo";
					text[i][1] = todo.getOwner();
					text[i][2] = String.valueOf(todo.isPublic());
					text[i][3] = todo.getPriority();
					text[i][7] = todo.toCalendarString();
					text[i][8] = todo.getText();
				}else if(pimCollection.get(i) instanceof PIMAppointment) {
					PIMAppointment todo = (PIMAppointment)(pimCollection.get(i));
					text[i][0] = "appointment";
					text[i][1] = todo.getOwner();
					text[i][2] = String.valueOf(todo.isPublic());
					text[i][3] = todo.getPriority();
					text[i][7] = todo.toCalendarString();
					text[i][8] = todo.getText();
				}else if(pimCollection.get(i) instanceof PIMNote) {
					PIMNote todo = (PIMNote)(pimCollection.get(i));
					text[i][0] = "note";
					text[i][1] = todo.getOwner();
					text[i][2] = String.valueOf(todo.isPublic());
					text[i][3] = todo.getPriority();
					text[i][8] = todo.getText();
				}
				
			}
			this.fireTableDataChanged();//同时通知JTable数据对象发生更改
		}
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return columnNames.length; //列数
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return pimCollection.size(); //行数
		}
		//设置单元格的内容
		@Override
		public Object getValueAt(int row,int col) {
			// TODO Auto-generated method stub
			return text[row][col];
		}
		//设置表格列名
		@Override
		public String getColumnName(int col) {
			// TODO Auto-generated method stub
			return columnNames[col];
		}
		@Override
		public void setValueAt(Object arg0, int row, int col) {
			// TODO Auto-generated method stub
			String oldValue = text[row][col];
			String newValue = (String)arg0;
			System.out.println(oldValue+" "+newValue);
			if(oldValue.equals(newValue))
				return;
			if("contact".equals(text[row][0])) {
				String sql = "delete from contact where owner = '"+(String)(text[row][1]) +"' and isPublic = '"+(String)(text[row][2])
						+"' and priority = '"+(String)(text[row][3])  +"' and firstname = '"+(String)(text[row][4])
						+"' and lastname = '"+(String)(text[row][5]) +"' and address = '"+(String)(text[row][6])+"';";
				System.out.println(sql);
				sqlHelper.update(sql);
				text[row][col] = newValue;
				sql = "insert into contact values('"+(String)(text[row][1]) +"','"+(String)(text[row][2])
						+"' ,'"+(String)(text[row][3])  +"' , '"+(String)(text[row][4])+"','"+(String)(text[row][5])+"','"+(String)(text[row][6])+"');";
				System.out.println(sql);
				sqlHelper.update(sql);
			}else if("todo".equals(text[row][0])) {
				String sql = "delete from todo where owner = '"+(String)(text[row][1]) +"' and isPublic = '"+(String)(text[row][2])
						+"' and priority = '"+(String)(text[row][3])  +"' and data = '"+(String)(text[row][7])+"' and text = '"+(String)(text[row][8])+"';";
				System.out.println(sql);
				sqlHelper.update(sql);
				text[row][col] = newValue;
				sql = "insert into todo values('"+(String)(text[row][1]) +"','"+(String)(text[row][2])
						+"' ,'"+(String)(text[row][3])  +"' , '"+(String)(text[row][7])+"','"+(String)(text[row][8])+"');";
				System.out.println(sql);
				sqlHelper.update(sql);
			}else if("appointment".equals(text[row][0])) {
				String sql = "delete from appointment where owner = '"+(String)(text[row][1]) +"' and isPublic = '"+(String)(text[row][2])
						+"' and priority = '"+(String)(text[row][3])  +"' and data = '"+(String)(text[row][7])+"' and text = '"+(String)(text[row][8])+"';";
				System.out.println(sql);
				sqlHelper.update(sql);
				text[row][col] = newValue;
				sql = "insert into appointment values('"+(String)(text[row][1]) +"','"+(String)(text[row][2])
						+"' ,'"+(String)(text[row][3])  +"' , '"+(String)(text[row][7])+"','"+(String)(text[row][8])+"');";
				System.out.println(sql);
				sqlHelper.update(sql);
			}else if("note".equals(text[row][0])) {
				String sql = "delete from note where owner = '"+(String)(text[row][1]) +"' and isPublic = '"+(String)(text[row][2])
						+"' and priority = '"+(String)(text[row][3])+"' and text = '"+(String)(text[row][8])+"';";
				System.out.println(sql);
				sqlHelper.update(sql);
				text[row][col] = newValue;
				sql = "insert into note values('"+(String)(text[row][1]) +"','"+(String)(text[row][2])
						+"' ,'"+(String)(text[row][3])+"','"+(String)(text[row][8])+"');";
				System.out.println(sql);
				sqlHelper.update(sql);
			}

			
			this.fireTableDataChanged();
		}
	}

}
