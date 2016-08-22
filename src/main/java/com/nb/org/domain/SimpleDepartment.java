/**
 * 
 */
package com.nb.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**   
* @Title: SimpleDepartment.java 
* @Package com.nb.org.domain 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Ishadow  
* @date 2016年8月22日 下午5:02:36 
* @version V1.0   
*/
public class SimpleDepartment {
	  private Integer id;
	    
	    /**
	     * @Fields: sn
	     * @Todo: 部门唯一标识号
	     */ 
	    private String sn;
	    
	    /**
	     * @Fields: name
	     * @Todo: 部门名称（可能是简略名称，如办公室）
	     */ 
	    private String name;
	    
	    /**
	     * @Fields: fullname
	     * @Todo: 部门全称（部门的全称，如宁波市公安局办公室）
	     */ 
	    private String fullname;
	    
	    /**
	     * @Fields: contactPerson
	     * @Todo: 部门的联系人
	     */ 
	    private String contactPerson;
	    
	    /**
	     * @Fields: contactNumber
	     * @Todo: 部门的联系方式
	     */ 
	    private String contactNumber;
	    
	    /**
	     * @Fields: address
	     * @Todo: 部门的地址
	     */ 
	    private String address;
	    
	    /**
	     * @Fields: description
	     * @Todo: 部门的简介或描述
	     */ 
	    private String description;
	    
	    /*
	     * upshi 20160219 转json时不包含父部门信息，递归很深jackson会报错
	     * */
	    /**
	     * @Fields: parentdep
	     * @Todo: 该部门的上级部门（父部门）
	     */ 
	    private Integer pid;

		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * @return the sn
		 */
		public String getSn() {
			return sn;
		}

		/**
		 * @param sn the sn to set
		 */
		public void setSn(String sn) {
			this.sn = sn;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the fullname
		 */
		public String getFullname() {
			return fullname;
		}

		/**
		 * @param fullname the fullname to set
		 */
		public void setFullname(String fullname) {
			this.fullname = fullname;
		}

		/**
		 * @return the contactPerson
		 */
		public String getContactPerson() {
			return contactPerson;
		}

		/**
		 * @param contactPerson the contactPerson to set
		 */
		public void setContactPerson(String contactPerson) {
			this.contactPerson = contactPerson;
		}

		/**
		 * @return the contactNumber
		 */
		public String getContactNumber() {
			return contactNumber;
		}

		/**
		 * @param contactNumber the contactNumber to set
		 */
		public void setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
		}

		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}

		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return the pid
		 */
		public Integer getPid() {
			return pid;
		}

		/**
		 * @param pid the pid to set
		 */
		public void setPid(Integer pid) {
			this.pid = pid;
		}

	
}
