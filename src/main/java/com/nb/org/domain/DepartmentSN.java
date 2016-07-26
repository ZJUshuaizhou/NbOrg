package com.nb.org.domain;


/**
 * 为了方便生成部门SN编号而产生的POJO
 * SN由base和number组合而成,base是parentDeptId部门的SN,number表示父部门下的子部门编号(两位16进制数字)
 * @author upshi
 * @date 20160121
 */
public class DepartmentSN {
	
    private Integer id;

    //新增部门时父部门的ID
    private Integer parentDeptId;	

    /**
     * 该属性在flag取值不同时有不同含义：
     * 	 flag == 0 时,number表示子部门编号的最大值,该POJO记录的含义是：parentDeptId部门下的子部门的编号(number)的最大值(两位16进制数字)
     *   flag == 1 时,number表示被删除的子部门的number值,该POJO记录的含义是：parentDeptId部门下的编号为number的子部门被删除(此时保存在表中为了以后再次利用)
     */
    private String number;			

    //parentDeptId部门的SN
    private String base;

    //含义参见number属性注释,只有0和1两种取值
    private Integer flag;

    public Integer getId() {
        return id;
    }

    
	public DepartmentSN() {}

	public DepartmentSN(Integer parentDeptId, String number, String base, Integer flag) {
		super();
		this.parentDeptId = parentDeptId;
		this.number = number;
		this.base = base;
		this.flag = flag;
	}

	public DepartmentSN(Integer id, Integer parentDeptId, String number, String base, Integer flag) {
		this.id = id;
		this.parentDeptId = parentDeptId;
		this.number = number;
		this.base = base;
		this.flag = flag;
	}


	public Integer getParentDeptId() {
		return parentDeptId;
	}


	public void setParentDeptId(Integer parentDeptId) {
		this.parentDeptId = parentDeptId;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getBase() {
		return base;
	}


	public void setBase(String base) {
		this.base = base;
	}


	public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String toString() {
		return "DepartmentSN [id=" + id + ", parentDeptId=" + parentDeptId + ", number=" + number + ", base=" + base + ", flag=" + flag + "]";
	}

}