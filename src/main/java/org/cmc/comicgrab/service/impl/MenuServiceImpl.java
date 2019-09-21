package org.cmc.comicgrab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.cmc.comicgrab.entity.Menu;
import org.cmc.comicgrab.entity.Role;
import org.cmc.comicgrab.mapper.MenuMapper;
import org.cmc.comicgrab.mapper.RoleMinistriesRelationMapper;
import org.cmc.comicgrab.mapper.UserMinistriesMapper;
import org.cmc.comicgrab.service.IMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 菜单-三共平台 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-08-23
 */
@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
	@Resource
	private MenuMapper menuMapper;
	@Resource
	private UserMinistriesMapper userMinistriesMapper;
	@Resource
	private RoleMinistriesRelationMapper roleMinistriesRelationMapper;

	@Override
	public List<Menu> getMenuTree(Integer parentId) {
		return menuMapper.getMenuTree(parentId);
	}

	@Override
	public List<Menu> getMenuListFromTree(Integer parentId) {
		List<Menu> menus = getMenuTree(parentId);
		List<Menu> menus2return = new ArrayList<>();
		recAdd2List(menus, menus2return);
		return menus2return;
	}

	private void recAdd2List(List<Menu> menus, List<Menu> menus2return) {
		for (Menu menu : menus) {
			menus2return.add(menu);
			if (menu.getChildren().size() > 0) {
				recAdd2List(menu.getChildren(), menus2return);
			}
		}
	}

	@Override
	public Set<String> getPermissionMenuByRoles(Integer userId) {
		QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
		queryWrapper.inSql("id_", "SELECT menu_id_ FROM role_permission_relation_ WHERE role_id_ IN ( SELECT role_id_ FROM role_ministries_relation_ WHERE ministries_id_ IN ( SELECT ministries_id_ FROM user_ministries_ WHERE user_id_="+userId+" UNION SELECT master_id_ FROM common_relation_ WHERE relation_type_='VICE_MINISTRIES' AND slave_id_="+userId+") )");
		List<Menu> menus = menuMapper.selectList(queryWrapper);
		return menus.stream().map(Menu::getName).collect(Collectors.toSet());
	}

}
