package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import dao.IDataUtilDAO;
import dbc.DatabaseConnection;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import service.Global;

public class DataUtilDAOImpl implements IDataUtilDAO {

	@Override
	public ArrayList<Object> getXMLAsArray(String path, String className) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Object> objlist = new ArrayList<Object>();

		Class cla = Class.forName(className);

		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(path));

		Element root = document.getRootElement(); // 根节点

		// 获得根节点下的所有子节点
		List<Element> lists = root.elements();
		for (Element e : lists) {
			Object obj = cla.newInstance();
			// 获得子节点下的所有孙子节点
			List<Element> sublists = e.elements();

			for (Element sube : sublists) {
				// 实例set方法
				// setXxx方法名
				String elementName = sube.getName();
				String data = sube.getStringValue();
				Field field = cla.getDeclaredField(elementName);
				Object value = convertObject(data, field.getType().getSimpleName());
				String methodName = "set" + elementName.substring(0, 1).toUpperCase() + elementName.substring(1);
				Method method = cla.getDeclaredMethod(methodName, field.getType());
				method.invoke(obj, value);
			}
			objlist.add(obj);
		}
		return objlist;
	}

	@Override
	public ArrayList<Object> getTXTAsArray(String path, String className) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Object> objlist = new ArrayList<Object>();
		Class cla = Class.forName(className);

		// 获得相应className类的字段属性
		Field fields[] = cla.getDeclaredFields();
		FileInputStream file = new FileInputStream(path);
		InputStreamReader fr = new InputStreamReader(file, "UTF-8");

		BufferedReader bf = new BufferedReader(fr);

		String data;

		while ((data = bf.readLine()) != null) {
			String str[] = data.split("\\s+");

			Object obj = cla.newInstance();
			for (int i = 0; i < str.length; i++) {
				Object value = convertObject(str[i], fields[i].getType().getSimpleName());
				// 获取setXxx方法名
				String methodName = "set" + fields[i].getName().substring(0, 1).toUpperCase()
						+ fields[i].getName().substring(1);
				Method method = cla.getDeclaredMethod(methodName, fields[i].getType());
				method.invoke(obj, value);
			}
			objlist.add(obj);
		}
		return objlist;
	}

	@Override
	public ArrayList<Object> getXLSAsArray(String path, String className) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Object> objlist = new ArrayList<Object>();
		Class cla = Class.forName(className);

		// 获得相应className类的字段属性
		Field fields[] = cla.getDeclaredFields();
		Workbook workbook = Workbook.getWorkbook(new File(path));

		Sheet sheet = workbook.getSheet(0);

		for (int i = 0; i < sheet.getRows(); i++) {
			String str[] = new String[sheet.getColumns()];
			Object obj = cla.newInstance();
			for (int j = 0; j < sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j, i);

				str[j] = cell.getContents();
				Object value = convertObject(str[j], fields[j].getType().getSimpleName());
				// 获取setXxx方法名
				String methodName = "set" + fields[j].getName().substring(0, 1).toUpperCase()
						+ fields[j].getName().substring(1);
				Method method = cla.getDeclaredMethod(methodName, fields[j].getType());
				method.invoke(obj, value);
			}
			objlist.add(obj);
		}

		return objlist;
	}

	@Override
	public void arraytoXML(ArrayList<Object> list, String className, String path) throws Exception {
		// TODO Auto-generated method stub
		Class cla = Class.forName(className);
		Document document = DocumentHelper.createDocument();
		String rootName = list.getClass().getSimpleName();// 获得类名
		Element root = document.addElement(rootName + "s");// 添加根节点，类名+s
		Field fields[] = cla.getDeclaredFields();// 获得实体类的所有属性

		for (Object obj : list) {
			Element body = root.addElement(rootName);

			for (int i = 0; i < fields.length; i++) {
				// 反射get方法
				Method method = obj.getClass().getMethod(
						"get" + fields[i].getName().substring(0, 1).toUpperCase() + fields[i].getName().substring(1));

				// 为孙子节点添加属性
				body.addElement(fields[i].getName()).setText(method.invoke(obj).toString());
			}
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8"); // 指定XML文档申明处的编码
		File file = new File(path);
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
		XMLWriter writer = new XMLWriter(osw, format);
		writer.write(document);
		writer.close();
		System.out.println("写入XML文件成功，存放路径为:" + path);

	}

	@Override
	public void arraytoTXT(ArrayList<Object> list, String className, String path) throws Exception {
		// TODO Auto-generated method stub
		Class cla = Class.forName(className);
		Field fields[] = cla.getDeclaredFields();// 获得实体类的所有属性
		File file = new File(path);
		PrintWriter pw = new PrintWriter(new FileWriter(file));

		for (Object obj : list) {
			pw.println(obj.toString());
		}
		System.out.println("写入TXT文件成功，存放路径为:" + path);
		pw.close();
	}

	@Override
	public void arraytoXLS(ArrayList<Object> list, String className, String path) throws Exception {
		// TODO Auto-generated method stub
		WritableWorkbook book;

		// 根据路径生成excel文件
		book = Workbook.createWorkbook(new File(path));
		// 创建一个sheet
		WritableSheet sheet = book.createSheet(className, 0);
		Label label = null;
		WritableCellFormat wcf = getHeaderCellStyle();
		int bodyLen = list.size();
		for (int j = 0; j < bodyLen; j++) {
			String[] bodyTempArr = list.get(j).toString().split("\\s+");
			for (int k = 0; k < bodyTempArr.length; k++) {
				WritableCellFormat tempCellFormat = null;
				tempCellFormat = getBodyCellStyle();
				if (tempCellFormat != null) {
					if (k == 0 || k == (bodyTempArr.length - 1)) {
						tempCellFormat.setAlignment(Alignment.CENTRE);
					}
				}
				label = new Label(k, 1 + j, bodyTempArr[k], tempCellFormat);
				sheet.addCell(label);
			}
		}
		System.out.println("成功写入" + list.size() + "条文件到excel表中!,存储路径为:" + path);
		book.write();
		book.close();

	}

	public WritableCellFormat getHeaderCellStyle() {
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat headerFormat = new WritableCellFormat(NumberFormats.TEXT);
		System.out.println("是否为表头设置属性(否为默认)?");
		Scanner sc = new Scanner(System.in);
		String choice = sc.next();
		
		Colour col= Colour.WHITE;
		Colour colWord = Colour.BLACK;
		BorderLineStyle border = BorderLineStyle.THIN;
		if(choice.equals("是")) {
			System.out.println("输入想要的背景色");
			String color = sc.next();
			switch(color) {
			case "蓝色":
				 col = Colour.BLUE;
				break;
			case "黄色":
				 col = Colour.YELLOW;
				break;
			case "红色":
				 col = Colour.RED;
				 break;
			case "绿色":
				 col = Colour.GREEN;
				break;
			case "灰色":
				 col = Colour.GREY_40_PERCENT;
				break;
				default:System.out.println("暂不支持该颜色!（默认白色）");
				 col = Colour.WHITE;
			}
			System.out.println("字体是否加粗(默认宋体,不加粗)");
			String bor = sc.next();
			if(bor.equals("是")) {
				border = BorderLineStyle.THICK;
			}
			System.out.println("输入标题的字体颜色");
			String coltitle = sc.next();
			switch(coltitle) {
			case "蓝色":
				colWord = Colour.BLUE;
				break;
			case "黄色":
				colWord = Colour.YELLOW;
				break;
			case "红色":
				colWord = Colour.RED;
				 break;
			case "绿色":
				colWord = Colour.GREEN;
				break;
			case "灰色":
				colWord = Colour.GREY_40_PERCENT;
				break;
				default:System.out.println("暂不支持该颜色!（默认黑色）");
				 col = Colour.BLACK;
					break;
			}
		}
		try {
			// 添加字体设置
			headerFormat.setFont(font);
			// 设置单元格背景色：默认为白色
			headerFormat.setBackground(col);
			// 设置表头表格边框样式
			// 整个表格线为粗线、黑色
			headerFormat.setBorder(Border.ALL, border, colWord);
			// 表头内容水平居中显示
			headerFormat.setAlignment(Alignment.CENTRE);
		} catch (WriteException e) {
			System.out.println("表头单元格样式设置失败！");
		}
		return headerFormat;
	}
	
	public WritableCellFormat getBodyCellStyle() {
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}
	public static Object convertObject(String data, String type) {
		Object obj = null;
		switch (type) {
		case "String":
			obj = new String(data);
			break;
		case "double":
		case "Double":
			obj = new Double(data);
			break;
		case "float":
		case "Float":
			obj = new Float(data);
			break;
		case "int":
		case "Integer":
			obj = new Integer(data);
			break;
		// 后面可以扩充转换为其他数据类型
		}
		return obj;
	}

	@Override
	public ArrayList<Object> getTableAsArray(String tableName, String className) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Object> objlist = new ArrayList<Object>();

		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection(); // 获取连接

		String sql = "select * from " + tableName;
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery(); // 执行查询
		ResultSetMetaData md = rs.getMetaData();// 得到结果集列的属性

		Class cla = Class.forName(className); // 获取指定类的Class对象
		while (rs.next()) { // 对记录集进行遍历
			Object obj = cla.newInstance(); // 动态创建一个新的对象
			for (int i = 1; i <= md.getColumnCount(); i++) { // 遍历所有的列
				String ColumnName = md.getColumnName(i); // 获取列字段名
				Field field = cla.getDeclaredField(ColumnName); // 创建该字段对应的Field对象
				Object value = rs.getObject(ColumnName); // 获取当前记录当前字段的值
				value = convertObject(value); // 将object类型转换为vo中定义的类型
				String methodName = "set" + ColumnName.substring(0, 1).toUpperCase() + ColumnName.substring(1); // 获取setXxx方法名
				Method method = cla.getDeclaredMethod(methodName, field.getType()); // 获取setXxxx方法对应的Method对象
				method.invoke(obj, value); // 执行对应的setXxxx方法
			}
			objlist.add(obj); // 将对象增加到集合中
		}
		pst.close();
		con.close();
		dbc.close();
		return objlist;
	}

	@Override
	public void arraytoTable(ArrayList<Object> list, String className, String tableName) throws Exception {
		// TODO Auto-generated method stub
		Class cla = Class.forName(className);
		Field fields[] = cla.getDeclaredFields();// 获得实体类的所有属性
		ArrayList<Object> objlist = new ArrayList<Object>();

		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection(); // 获取连接

		// Class cla = Class.forName(tableName); // 获取指定类的Class对象
		// Field fields[] = list.getClass().getDeclaredFields(); // 创建该表对应的Field对象
		for (Object obj : list) {
			String ele = "";
			for (int i = 0; i < fields.length; i++) {

				if (i == fields.length - 1) {
					ele = ele + "?";
				} else {
					ele = ele + "?" + ",";
				}
			}
			String sql = "insert into " + tableName + "\nvalues (" + ele + ")";
			PreparedStatement pst = con.prepareStatement(sql);
			for (int i = 0; i < fields.length; i++) {
				// 获取get方法名
				Method method = obj.getClass().getMethod(
						"get" + fields[i].getName().substring(0, 1).toUpperCase() + fields[i].getName().substring(1));
				String value = method.invoke(obj).toString();
				// System.out.println(value);
				switch (fields[i].getType().getSimpleName()) {
				case "String":
					pst.setString(i + 1, value);
					break;
				case "int":
				case "Integer":
					pst.setInt(i + 1, Integer.parseInt(value));
					break;
				case "float":
				case "Float":
					pst.setFloat(i + 1, Float.parseFloat(value));
					break;
				case "double":
				case "Double":
					pst.setDouble(i + 1, Double.parseDouble(value));
					break;
				case "Date":
					pst.setDate(i + 1, Date.valueOf(value));
					break;
				case "Timestamp":
					pst.setTimestamp(i + 1, Timestamp.valueOf(value));
					break;
				}
			}
			pst.executeUpdate();
		}
		System.out.println("成功写入" + list.size() + "条数据到数据库，存放表名为:" + tableName);

		con.close();
		dbc.close();

	}

	// 将从数据库中读出的数据转换为VO中定义的Java数据类型
	public static Object convertObject(Object value) {
		String ColumnType = value.getClass().getSimpleName();
		switch (ColumnType) {
		case "Timestamp":
			value = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(value);
			break;
		case "Date": // 将数据库中的Date类型数据转为vo中定义的String类型
			value = new String(value.toString());
			break;
		case "BigDecimal": // 将从数据库中读出的BigDecimal类型数据转换为vo中定义的double类型
			value = ((BigDecimal) value).doubleValue();
			break;
		// 后面可以扩充其他类型的数据转换
		}
		return value;
	}

}
