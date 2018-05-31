package GUI;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import five.PIMCollection;
import five.PIMTodo;
import sql.SQLHelper;

public class ShowTodo extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;
	private PIMCollection pimCollection;
	private MyTableModel model;
	private SQLHelper sqlHelper;
	private String[][] text ;
	
	public ShowTodo(PIMCollection pimCollection,String msg,SQLHelper sqlHelper){
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
		String[] columnNames = {"owner","isPublic","priority","date","description"};
		private static final long serialVersionUID = 1L;
		
		
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}
		public void setDays() {
			text = new String[pimCollection.size()][columnNames.length];
			for(int i = 0; i < pimCollection.size(); i++) {
				PIMTodo todo = (PIMTodo)(pimCollection.get(i));
				text[i][0] = todo.getOwner();
				text[i][1] = String.valueOf(todo.isPublic());
				text[i][2] = todo.getPriority();
				text[i][3] = todo.toCalendarString();
				text[i][4] = todo.getText();
				
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
			String sql = "delete from todo where owner = '"+(String)(text[row][0]) +"' and isPublic = '"+(String)(text[row][1])
					+"' and priority = '"+(String)(text[row][2])  +"' and data = '"+(String)(text[row][3])+"' and text = '"+(String)(text[row][4])+"';";
			System.out.println(sql);
			sqlHelper.update(sql);
			text[row][col] = newValue;
			sql = "insert into todo values('"+(String)(text[row][0]) +"','"+(String)(text[row][1])
					+"' ,'"+(String)(text[row][2])  +"' , '"+(String)(text[row][3])+"','"+(String)(text[row][4])+"');";
			System.out.println(sql);
			sqlHelper.update(sql);
			
			this.fireTableDataChanged();
		}
	}

}
