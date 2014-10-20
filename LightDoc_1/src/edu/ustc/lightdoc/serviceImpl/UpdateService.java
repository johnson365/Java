package edu.ustc.lightdoc.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import edu.ustc.lightdoc.entity.Audit;
import edu.ustc.lightdoc.entity.Function;
import edu.ustc.lightdoc.entity.Step;
import edu.ustc.lightdoc.entity.StepSet;
import edu.ustc.lightdoc.parser.ICfgDOMHelper;
import edu.ustc.lightdoc.parser.IDOMHelper;
import edu.ustc.lightdoc.service.IUpdateService;

public class UpdateService implements IUpdateService {

	private IDOMHelper domHelper;
	private ICfgDOMHelper cfgDomHelper;

	public IDOMHelper getDomHelper() {
		return domHelper;
	}

	public void setDomHelper(IDOMHelper domHelper) {
		this.domHelper = domHelper;
	}

	public ICfgDOMHelper getCfgDomHelper() {
		return cfgDomHelper;
	}

	public void setCfgDomHelper(ICfgDOMHelper cfgDomHelper) {
		this.cfgDomHelper = cfgDomHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ustc.lightdoc.serviceImpl.IUpdateService#modifyXml(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean modifyXml(String fileName, String value) {
		boolean result = this.cfgDomHelper.modifyXmlFile(fileName, value);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ustc.lightdoc.serviceImpl.IUpdateService#modifyElementByPath(java
	 * .lang.String, java.lang.String, java.lang.String,
	 * edu.ustc.lightdoc.entity.Step)
	 */
	@Override
	public boolean modifyElementByPath(String fileUrl, String moduleTitle,
			String funcTitle, String currPg, String num, Step step) {
		String path = "software/modules/module?/funcs/func?/stepSet!/step!";
		List<String> conditions = new ArrayList<String>();
		conditions.add(moduleTitle);
		conditions.add(funcTitle);
		int curr = Integer.parseInt(currPg);
		conditions.add(curr + "");
		int tmp = Integer.parseInt(num) + 1;
		conditions.add(tmp + "");
		String xpath = this.domHelper.toXpath(path, conditions);// ��ݲ����������path
		boolean result = this.domHelper.modifyElementByPath(xpath, fileUrl,
				step);
		return result;
	}

	public boolean addNodeToXml(Audit audit) {
		boolean result = false;
		if (!audit.getFileName().equals("") && !audit.getUrl().equals("")) {
			result = this.cfgDomHelper.addNode(audit.getFileName(),
					audit.getUrl());
		}
		return result;
	}

	public List<Audit> xmlFileList() {
		List<Audit> listOfXML = this.cfgDomHelper.getAllXMLFile();
		for (int i = 0; i < listOfXML.size(); i++) {
			Audit a = listOfXML.get(i);
			String tmp = (a.getStatus().equals("true")) ? "已审核" : "未审核";
			a.setStatus(tmp);
		}
		return listOfXML;
	}

	@Override
	public boolean addElementByPath(String fileUrl, String moduleTitle,
			String funcTitle, String currPg, String num, Step step) {
		String path = "software/modules/module?/funcs/func?/stepSet!";
		List<String> conditions = new ArrayList<String>();
		conditions.add(moduleTitle);
		conditions.add(funcTitle);
		int tmp = Integer.parseInt(currPg);
		conditions.add(tmp + "");
		int number = Integer.parseInt(num);
		String xpath = this.domHelper.toXpath(path, conditions);// ��ݲ����������path
		boolean result = this.domHelper.addElementByPath(xpath, fileUrl,
				number, step);
		return result;
	}

	@Override
	public boolean delElementByPath(String fileUrl, String moduleTitle,
			String funcTitle, String currPg, String num) {
		String path = "software/modules/module?/funcs/func?/stepSet!";
		List<String> conditions = new ArrayList<String>();
		conditions.add(moduleTitle);
		conditions.add(funcTitle);
		int tmp = Integer.parseInt(currPg);
		conditions.add(tmp + "");
		int number = Integer.parseInt(num);
		String xpath = this.domHelper.toXpath(path, conditions);// ��ݲ����������path
		boolean result = this.domHelper
				.delElementByPath(xpath, number, fileUrl);
		return result;
	}

	@Override
	public boolean uploadFile(String path, String fileName, File file) {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			// �����ļ������
			path = path.substring(0, path.lastIndexOf("\\"));
			path = path + "\\slide";
			System.out.println(path);
			fos = new FileOutputStream(path + "\\" + fileName);
			// �����ļ��ϴ���
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			return true;
		} catch (Exception e) {
			System.out.println("�ļ��ϴ�ʧ��");
			e.printStackTrace();
			return false;
		} finally {
			close(fos, fis);
		}
	}

	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				System.out.println("FileInputStream�ر�ʧ��");
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				System.out.println("FileOutputStream�ر�ʧ��");
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean addSSToFunction(String fileUrl, String module, String func,
			int currPg, String bgPath) {
		String path = "software/modules/module?/funcs/func?";
		List<String> conditions = new ArrayList<String>();
		conditions.add(module);
		conditions.add(func);
		String xpath = this.domHelper.toXpath(path, conditions);// ��ݲ����������path
		StepSet ss = new StepSet();
		ss.setImgSrc(bgPath);
		return this.domHelper.addSSToFunction(fileUrl, xpath, currPg, ss);
	}

	@Override
	public boolean updateFunc(String fileUrl, String moduleTitle,
			String funcTitle, Function func) {
		String path = "software/modules/module?/funcs/func?";
		List<String> conditions = new ArrayList<String>();
		conditions.add(moduleTitle);
		conditions.add(funcTitle);
		String xpath = this.domHelper.toXpath(path, conditions);// ��ݲ����������path

		return this.domHelper.updateFunc(fileUrl, xpath, func);
	}

	@Override
	public boolean updateSoft(String fileUrl, String title, String desc) {
		return this.domHelper.updateSoft(fileUrl, title, desc);
	}
}
