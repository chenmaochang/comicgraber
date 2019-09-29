package org.cmc.comicgrab.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import org.cmc.comicgrab.entity.Ministries;

/**
 * <p>
 * 主管部门 服务类
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
public interface IMinistriesService extends IService<Ministries> {
	/**获取部门树形列表
	 *2019年9月2日 上午11:04:04
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param parentId
	 * @return
	 */
	public List<Ministries> getMinistriesListFromTree(Integer parentId);

	/**生成路径
	 *2019年9月2日 上午11:54:16
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param ministries
	 */
	default void generatePath(Ministries ministries) {
		if(ministries.getParentId()!=null&&ministries.getParentId()!=0) {
			Ministries parentMinistries=getById(ministries.getParentId());
			ministries.setPath(parentMinistries.getPath()+"-"+ministries.getId());
		}else {
			ministries.setPath("0-"+ministries.getId());
		}
	}

	/**通过用户id获取唯一的部门,没有如果,用户有且仅有一个主部门
	 *2019年9月18日 下午2:16:01
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param userId
	 * @return
	 */
	Ministries getMinistriesByUserId(Integer userId);
}
