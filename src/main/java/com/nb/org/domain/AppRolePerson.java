package com.nb.org.domain;

public class AppRolePerson {
private Integer id;
private AppRole appRole;
private Person person;
public AppRolePerson() {
	super();
	// TODO Auto-generated constructor stub
}
public AppRolePerson(Integer id, AppRole appRole, Person person) {
	super();
	this.id = id;
	this.appRole = appRole;
	this.person = person;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public AppRole getAppRole() {
	return appRole;
}
public void setAppRole(AppRole appRole) {
	this.appRole = appRole;
}
public Person getPerson() {
	return person;
}
public void setPerson(Person person) {
	this.person = person;
}
@Override
public String toString() {
	return "AppRolePerson [id=" + id + ", appRole=" + appRole + ", person=" + person + "]";
}





}
