package com.cstm.domain;

import java.util.List;


/**
 * ��ʼ������ҳ
 * @author Gloria
 *
 */
public class PageBean<T>{
	private int pc;//��ǰҳ��page code
	//private int tp;//��ҳ��total page,��ϵͳ��������ģ��������������������
	private int tr;//�ܼ�¼��total record
	private int ps;//ÿҳ��¼��page size
	private List<T>beanList;//��ǰҳ�ļ�¼ 
	
	private String url;//�������url���Զ�λ����ͬ��ҳ�棬��ҳ����ʾ��ͬ��ҳ��
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	
	/**
	 * ����ϵͳ����ҳ��
	 * @return
	 */
	public int getTp() {
		int tp=tr/ps;
		return tr%ps==0? tp:tp+1;
	}
//	public void setTp(int tp) {
//		this.tp = tp;
//	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
}
