package org.cmc.comicgrab.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * layui 扩展ele-tree包装类
 * 
 * @author admin
 *
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class TreeItemWrapper {
	private String id;
	private String label;
	private String type;
}
