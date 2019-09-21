package org.cmc.comicgrab.service;

import java.util.List;
import java.util.Set;

import org.cmc.comicgrab.entity.Menu;
import org.cmc.comicgrab.entity.Role;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜单-三共平台 服务类
 * </p>
 *
 * @author cmc
 * @since 2019-08-23
 */
public interface IMenuService extends IService<Menu> {
	List<Menu> getMenuTree(Integer parentId);
	/**从tree型结构的menuList里获取平行的menuList
	 *2019年8月27日 下午1:48:59
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param parentId
	 * @return
	 */
	List<Menu> getMenuListFromTree(Integer parentId);
	
	/**通过角色list获取权限菜单
	 *2019年8月29日 下午2:44:17
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param roles
	 * @return
	 */
	Set<String> getPermissionMenuByRoles(Integer userId);
}
