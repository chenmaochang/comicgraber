package org.cmc.comicgrab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.cmc.comicgrab.entity.UserMinistries;
import org.cmc.comicgrab.mapper.UserMinistriesMapper;
import org.cmc.comicgrab.service.IUserMinistriesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户部门关系表 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-09-02
 */
@Service
@Transactional
public class UserMinistriesServiceImpl extends ServiceImpl<UserMinistriesMapper, UserMinistries> implements IUserMinistriesService {

}
