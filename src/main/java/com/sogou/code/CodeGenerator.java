package com.sogou.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sogou.code.vo.Attribute;
import com.sogou.code.vo.ColumnVO;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class CodeGenerator {
	
	public static void gen(String template,String packageName,String tableName,String directory) throws IOException, TemplateException, SQLException {
		String className = GetTableInfo.getCamelName(tableName);
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setDirectoryForTemplateLoading(new File(CodeGenerator.class.getResource("/").getPath() + "/templates"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		Template temp = cfg.getTemplate(template); // load E:/Work/Freemarker/templates/person.ftl

		// Create the root hash
		Map<String, Object> root = new HashMap<String, Object>();

		root.put("packageName", packageName);
		root.put("className", className);
		root.put("author", "");

		List<ColumnVO> cols = GetTableInfo.getColumns(tableName);
		root.put("attrs", cols);
		File dir = new File(directory+"/"+packageName.replaceAll("\\.", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		OutputStream fos = new FileOutputStream(new File(dir, className+".java")); // java文件的生成目录
		Writer out = new OutputStreamWriter(fos);
		temp.process(root, out);

		fos.flush();
		fos.close();

		System.out.println("gen code success!");
	}
	
	
	public static void genDao(String packageName,String tableName,String directory) throws IOException, TemplateException, SQLException {
		String className = GetTableInfo.getCamelName(tableName);
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setDirectoryForTemplateLoading(new File(CodeGenerator.class.getResource("/").getPath() + "/templates"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		Template temp = cfg.getTemplate("daoImpl.ftl"); // load E:/Work/Freemarker/templates/person.ftl

		// Create the root hash
		Map<String, Object> root = new HashMap<String, Object>();

		root.put("packageName", packageName);
		root.put("className", className);
		root.put("tableName", tableName);
		
		root.put("author", "");

		List<ColumnVO> cols = GetTableInfo.getColumns(tableName);
		root.put("attrs", cols);
		File dir = new File(directory+"/"+packageName.replaceAll("\\.", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		OutputStream fos = new FileOutputStream(new File(dir, className+"DaoImpl.java")); // java文件的生成目录
		Writer out = new OutputStreamWriter(fos);
		temp.process(root, out);

		fos.flush();
		fos.close();

		System.out.println("gen dao success!");
	}

	public void gen() throws IOException, TemplateException {

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setDirectoryForTemplateLoading(new File(CodeGenerator.class.getResource("/").getPath() + "/templates"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		Template temp = cfg.getTemplate("vo.ftl"); // load E:/Work/Freemarker/templates/person.ftl

		// Create the root hash
		Map<String, Object> root = new HashMap<String, Object>();

		root.put("packageName", "com.ricky.java");
		root.put("className", "Person");
		root.put("author", "Ricky Fung");

		List<Attribute> attr_list = new ArrayList<Attribute>();
		attr_list.add(new Attribute("id", "Long"));
		attr_list.add(new Attribute("name", "String"));
		attr_list.add(new Attribute("age", "Integer"));
		attr_list.add(new Attribute("hobby", "List<String>"));

		root.put("attrs", attr_list);
		File dir = new File("d:/freemarker");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		OutputStream fos = new FileOutputStream(new File(dir, "Person.java")); // java文件的生成目录
		Writer out = new OutputStreamWriter(fos);
		temp.process(root, out);

		fos.flush();
		fos.close();

		System.out.println("gen code success!");
	}
	
	

	public static void main(String[] args) throws Exception {

	//	CodeGenerator.gen("vo.ftl", "com.sogou.vo", "game_kaifu", "d:/files");
		CodeGenerator.genDao("com.sogou.dao", "game_kaifu", "d:/files");
	}
	
}
