package org.cmc.comicgrab.common;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.Data;

@Data
public class LayuiPageResult<T> {
	private Integer count;
	private List<T> data;
	private Integer code=0;
	public LayuiPageResult(IPage<T> page) {
		this.count=(int) page.getTotal();
		this.data=page.getRecords();
	}
	
}
