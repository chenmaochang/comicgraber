package org.cmc.comicgrab.mapper;

import java.util.List;

import org.cmc.comicgrab.entity.Menu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 菜单-三共平台 Mapper 接口
 * </p>
 *
 * @author cmc
 * @since 2019-08-23
 */
public interface MenuMapper extends BaseMapper<Menu> {
	List<Menu> getMenuTree(Integer parentId);
}
