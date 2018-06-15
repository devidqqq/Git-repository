package com.devid.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_supplier")
public class Supplier {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length=200)
	private String name; //供应商
	
	@Column(length=100)
	private String contact; //联系人
	
	@Column(length=100)
	private String number;
	
	@Column(length=100)
	private String address;
	
	@Column(length=200)
	private String remarks;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + ", contact=" + contact
				+ ", number=" + number + ", address=" + address + ", remarks="
				+ remarks + "]";
	}
	
	
}
