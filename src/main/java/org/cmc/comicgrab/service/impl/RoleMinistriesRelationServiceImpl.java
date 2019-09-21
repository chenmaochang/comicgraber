package org.cmc.comicgrab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.cmc.comicgrab.entity.RoleMinistriesRelation;
import org.cmc.comicgrab.mapper.RoleMinistriesRelationMapper;
import org.cmc.comicgrab.service.IRoleMinistriesRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色部门关联表 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-09-02
 */
@Service
@Transactional
public class RoleMinistriesRelationServiceImpl extends ServiceImpl<RoleMinistriesRelationMapper, RoleMinistriesRelation> implements IRoleMinistriesRelationService {

}
