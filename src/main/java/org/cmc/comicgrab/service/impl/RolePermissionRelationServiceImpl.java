package org.cmc.comicgrab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.entity.Menu;
import org.cmc.comicgrab.entity.RolePermissionRelation;
import org.cmc.comicgrab.mapper.MenuMapper;
import org.cmc.comicgrab.mapper.RolePermissionRelationMapper;
import org.cmc.comicgrab.service.IRolePermissionRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 菜单角色权限表 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-08-28
 */
@Service
@Transactional
public class RolePermissionRelationServiceImpl extends ServiceImpl<RolePermissionRelationMapper, RolePermissionRelation> implements IRolePermissionRelationService {
@Resource
private MenuMapper menuMapper;
	@Override
	public Map<String, String> loadAuthorizationFromDatabase() {
		Map<String, String> map=new LinkedHashMap<>();
		QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
		queryWrapper.ne("menu_type_", "VOID").eq("sys_status_", Constants.SystemConstants.NORMAL.getValue());
		menuMapper.selectList(queryWrapper).forEach(menu->map.put(menu.getMenuUrl(), "perms["+menu.getName()+"]"));;
		return map;
	}

}
