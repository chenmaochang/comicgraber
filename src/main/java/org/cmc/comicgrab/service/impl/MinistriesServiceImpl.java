package org.cmc.comicgrab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.cmc.comicgrab.entity.Ministries;
import org.cmc.comicgrab.mapper.MinistriesMapper;
import org.cmc.comicgrab.service.IMinistriesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 主管部门 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
@Service
@Transactional
public class MinistriesServiceImpl extends ServiceImpl<MinistriesMapper, Ministries> implements IMinistriesService {
@Resource
private MinistriesMapper ministriesMapper;
	@Override
	public List<Ministries> getMinistriesListFromTree(Integer parentId) {
		return ministriesMapper.getMinistriesTree(parentId);
	}

    @Override
    public Ministries getMinistriesByUserId(Integer userId) {
        return ministriesMapper.getMinistriesByUserId(userId);
    }

}
