package org.cmc.comicgrab.service;

import java.util.List;

import org.cmc.comicgrab.entity.ManagerUser;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
public interface IManagerUserService extends IService<ManagerUser> {
	public ManagerUser selectByAccount(String account);
	public List<ManagerUser> selectListByAccount(String account);
	public boolean changePassword(ManagerUser managerUser,String oldPassword,String newPassword);
}
