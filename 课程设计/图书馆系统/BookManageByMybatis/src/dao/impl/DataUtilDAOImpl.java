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

		Element root = document.getRootElement(); // ���ڵ�

		// ��ø��ڵ��µ������ӽڵ�
		List<Element> lists = root.elements();
		for (Element e : lists) {
			Object obj = cla.newInstance();
			// ����ӽڵ��µ��������ӽڵ�
			List<Element> sublists = e.elements();

			for (Element sube : sublists) {
				// ʵ��set����
				// setXxx������
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

		// �����ӦclassName����ֶ�����
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
				// ��ȡsetXxx������
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

		// �����ӦclassName����ֶ�����
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
				// ��ȡsetXxx������
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
		String rootName = list.getClass().getSimpleName();// �������
		Element root = document.addElement(rootName + "s");// ��Ӹ��ڵ㣬����+s
		Field fields[] = cla.getDeclaredFields();// ���ʵ�������������

		for (Object obj : list) {
			Element body = root.addElement(rootName);

			for (int i = 0; i < fields.length; i++) {
				// ����get����
				Method method = obj.getClass().getMethod(
						"get" + fields[i].getName().substring(0, 1).toUpperCase() + fields[i].getName().substring(1));

				// Ϊ���ӽڵ��������
				body.addElement(fields[i].getName()).setText(method.invoke(obj).toString());
			}
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8"); // ָ��XML�ĵ��������ı���
		File file = new File(path);
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
		XMLWriter writer = new XMLWriter(osw, format);
		writer.write(document);
		writer.close();
		System.out.println("д��XML�ļ��ɹ������·��Ϊ:" + path);

	}

	@Override
	public void arraytoTXT(ArrayList<Object> list, String className, String path) throws Exception {
		// TODO Auto-generated method stub
		Class cla = Class.forName(className);
		Field fields[] = cla.getDeclaredFields();// ���ʵ�������������
		File file = new File(path);
		PrintWriter pw = new PrintWriter(new FileWriter(file));

		for (Object obj : list) {
			pw.println(obj.toString());
		}
		System.out.println("д��TXT�ļ��ɹ������·��Ϊ:" + path);
		pw.close();
	}

	@Override
	public void arraytoXLS(ArrayList<Object> list, String className, String path) throws Exception {
		// TODO Auto-generated method stub
		WritableWorkbook book;

		// ����·������excel�ļ�
		book = Workbook.createWorkbook(new File(path));
		// ����һ��sheet
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
		System.out.println("�ɹ�д��" + list.size() + "���ļ���excel����!,�洢·��Ϊ:" + path);
		book.write();
		book.close();

	}

	public WritableCellFormat getHeaderCellStyle() {
		WritableFont font = new WritableFont(WritableFont.createFont("����"), 10, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat headerFormat = new WritableCellFormat(NumberFormats.TEXT);
		System.out.println("�Ƿ�Ϊ��ͷ��������(��ΪĬ��)?");
		Scanner sc = new Scanner(System.in);
		String choice = sc.next();
		
		Colour col= Colour.WHITE;
		Colour colWord = Colour.BLACK;
		BorderLineStyle border = BorderLineStyle.THIN;
		if(choice.equals("��")) {
			System.out.println("������Ҫ�ı���ɫ");
			String color = sc.next();
			switch(color) {
			case "��ɫ":
				 col = Colour.BLUE;
				break;
			case "��ɫ":
				 col = Colour.YELLOW;
				break;
			case "��ɫ":
				 col = Colour.RED;
				 break;
			case "��ɫ":
				 col = Colour.GREEN;
				break;
			case "��ɫ":
				 col = Colour.GREY_40_PERCENT;
				break;
				default:System.out.println("�ݲ�֧�ָ���ɫ!��Ĭ�ϰ�ɫ��");
				 col = Colour.WHITE;
			}
			System.out.println("�����Ƿ�Ӵ�(Ĭ������,���Ӵ�)");
			String bor = sc.next();
			if(bor.equals("��")) {
				border = BorderLineStyle.THICK;
			}
			System.out.println("��������������ɫ");
			String coltitle = sc.next();
			switch(coltitle) {
			case "��ɫ":
				colWord = Colour.BLUE;
				break;
			case "��ɫ":
				colWord = Colour.YELLOW;
				break;
			case "��ɫ":
				colWord = Colour.RED;
				 break;
			case "��ɫ":
				colWord = Colour.GREEN;
				break;
			case "��ɫ":
				colWord = Colour.GREY_40_PERCENT;
				break;
				default:System.out.println("�ݲ�֧�ָ���ɫ!��Ĭ�Ϻ�ɫ��");
				 col = Colour.BLACK;
					break;
			}
		}
		try {
			// �����������
			headerFormat.setFont(font);
			// ���õ�Ԫ�񱳾�ɫ��Ĭ��Ϊ��ɫ
			headerFormat.setBackground(col);
			// ���ñ�ͷ���߿���ʽ
			// ���������Ϊ���ߡ���ɫ
			headerFormat.setBorder(Border.ALL, border, colWord);
			// ��ͷ����ˮƽ������ʾ
			headerFormat.setAlignment(Alignment.CENTRE);
		} catch (WriteException e) {
			System.out.println("��ͷ��Ԫ����ʽ����ʧ�ܣ�");
		}
		return headerFormat;
	}
	
	public WritableCellFormat getBodyCellStyle() {
		WritableFont font = new WritableFont(WritableFont.createFont("����"), 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// ���õ�Ԫ�񱳾�ɫ������Ϊ��ɫ
			bodyFormat.setBackground(Colour.WHITE);
			// ���ñ�ͷ���߿���ʽ
			// ���������Ϊϸ�ߡ���ɫ
			bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			System.out.println("���嵥Ԫ����ʽ����ʧ�ܣ�");
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
		// �����������ת��Ϊ������������
		}
		return obj;
	}

	@Override
	public ArrayList<Object> getTableAsArray(String tableName, String className) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Object> objlist = new ArrayList<Object>();

		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection(); // ��ȡ����

		String sql = "select * from " + tableName;
		PreparedStatement pst = con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery(); // ִ�в�ѯ
		ResultSetMetaData md = rs.getMetaData();// �õ�������е�����

		Class cla = Class.forName(className); // ��ȡָ�����Class����
		while (rs.next()) { // �Լ�¼�����б���
			Object obj = cla.newInstance(); // ��̬����һ���µĶ���
			for (int i = 1; i <= md.getColumnCount(); i++) { // �������е���
				String ColumnName = md.getColumnName(i); // ��ȡ���ֶ���
				Field field = cla.getDeclaredField(ColumnName); // �������ֶζ�Ӧ��Field����
				Object value = rs.getObject(ColumnName); // ��ȡ��ǰ��¼��ǰ�ֶε�ֵ
				value = convertObject(value); // ��object����ת��Ϊvo�ж��������
				String methodName = "set" + ColumnName.substring(0, 1).toUpperCase() + ColumnName.substring(1); // ��ȡsetXxx������
				Method method = cla.getDeclaredMethod(methodName, field.getType()); // ��ȡsetXxxx������Ӧ��Method����
				method.invoke(obj, value); // ִ�ж�Ӧ��setXxxx����
			}
			objlist.add(obj); // ���������ӵ�������
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
		Field fields[] = cla.getDeclaredFields();// ���ʵ�������������
		ArrayList<Object> objlist = new ArrayList<Object>();

		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection(); // ��ȡ����

		// Class cla = Class.forName(tableName); // ��ȡָ�����Class����
		// Field fields[] = list.getClass().getDeclaredFields(); // �����ñ��Ӧ��Field����
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
				// ��ȡget������
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
		System.out.println("�ɹ�д��" + list.size() + "�����ݵ����ݿ⣬��ű���Ϊ:" + tableName);

		con.close();
		dbc.close();

	}

	// �������ݿ��ж���������ת��ΪVO�ж����Java��������
	public static Object convertObject(Object value) {
		String ColumnType = value.getClass().getSimpleName();
		switch (ColumnType) {
		case "Timestamp":
			value = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(value);
			break;
		case "Date": // �����ݿ��е�Date��������תΪvo�ж����String����
			value = new String(value.toString());
			break;
		case "BigDecimal": // �������ݿ��ж�����BigDecimal��������ת��Ϊvo�ж����double����
			value = ((BigDecimal) value).doubleValue();
			break;
		// ������������������͵�����ת��
		}
		return value;
	}

}
