package com.nb.org.domain;

public class SyncOrg {

	 private Integer id;
	 private String orgcoding;
	 private Integer depId;
	 private String devcoding;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrgcoding() {
		return orgcoding;
	}
	public void setOrgcoding(String orgcoding) {
		this.orgcoding = orgcoding;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getDevcoding() {
		return devcoding;
	}
	public void setDevcoding(String devcoding) {
		this.devcoding = devcoding;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((depId == null) ? 0 : depId.hashCode());
		result = prime * result + ((devcoding == null) ? 0 : devcoding.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orgcoding == null) ? 0 : orgcoding.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SyncOrg other = (SyncOrg) obj;
		if (depId == null) {
			if (other.depId != null)
				return false;
		} else if (!depId.equals(other.depId))
			return false;
		if (devcoding == null) {
			if (other.devcoding != null)
				return false;
		} else if (!devcoding.equals(other.devcoding))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orgcoding == null) {
			if (other.orgcoding != null)
				return false;
		} else if (!orgcoding.equals(other.orgcoding))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SyncOrg [id=" + id + ", orgcoding=" + orgcoding + ", depId=" + depId + ", devcoding=" + devcoding + "]";
	}
	
	 
}
