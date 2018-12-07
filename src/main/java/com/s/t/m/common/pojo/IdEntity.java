package com.s.t.m.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="通用接参实体")
public class IdEntity {
	
	@ApiModelProperty(value="主键",name="id")
	private Long id;
	@ApiModelProperty(value="主键数组",name="ids")
	private Long[] ids;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
}
