package org.cmc.comicgrab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.cmc.comicgrab.entity.CommonRelation;
import org.cmc.comicgrab.mapper.CommonRelationMapper;
import org.cmc.comicgrab.service.ICommonRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 通用关系表 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-09-09
 */
@Service
@Transactional
public class CommonRelationServiceImpl extends ServiceImpl<CommonRelationMapper, CommonRelation> implements ICommonRelationService {

}
