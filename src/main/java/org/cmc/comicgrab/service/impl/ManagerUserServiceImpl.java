package org.cmc.comicgrab.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cmc.comicgrab.entity.ManagerUser;
import org.cmc.comicgrab.mapper.ManagerUserMapper;
import org.cmc.comicgrab.service.IManagerUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
@Service
@Slf4j
@Transactional
public class ManagerUserServiceImpl extends ServiceImpl<ManagerUserMapper, ManagerUser> implements IManagerUserService {
	@Resource
	private ManagerUserMapper managerUserMapper;
	
	/**账号查找绝对存在的用户
	 *2019年8月21日 下午4:45:17
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param account
	 * @return
	 */
	public ManagerUser selectByAccount(String account) {
		try {
			QueryWrapper<ManagerUser> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("account_", account);
			ManagerUser managerUser=managerUserMapper.selectOne(queryWrapper);
			return managerUser;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**通过账号查找用户列表,有可能为空列表
	 *2019年8月22日 上午10:24:44
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param account
	 * @return
	 */
	public List<ManagerUser> selectListByAccount(String account) {
		try {
			QueryWrapper<ManagerUser> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("account_", account);
			List<ManagerUser> managerUsers=managerUserMapper.selectList(queryWrapper);
			return managerUsers;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**传入用户修改密码
	 *2019年8月21日 下午5:04:06
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param managerUser
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean changePassword(ManagerUser managerUser,String oldPassword,String newPassword) {
		try {
			String oldPasswordMD5=new String(DigestUtils.md5DigestAsHex(oldPassword.getBytes()));
			if(managerUser.getPassword().equals(oldPasswordMD5)) {//新旧密码校验
				managerUser.setPassword(new String(DigestUtils.md5DigestAsHex(newPassword.getBytes())));
				return managerUserMapper.updateById(managerUser)>=1;
			}else {
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return false;
	}
}
