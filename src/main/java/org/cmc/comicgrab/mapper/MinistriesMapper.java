package org.cmc.comicgrab.mapper;

import java.util.List;

import org.cmc.comicgrab.entity.Ministries;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 主管部门 Mapper 接口
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
public interface MinistriesMapper extends BaseMapper<Ministries> {

	public List<Ministries> getMinistriesTree(Integer parentId);
	public Ministries getMinistriesByUserId(Integer userId);
}
