package org.cmc.comicgrab.service.impl;

import org.cmc.comicgrab.entity.Episode;
import org.cmc.comicgrab.mapper.EpisodeMapper;
import org.cmc.comicgrab.service.IEpisodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 章节 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-09-29
 */
@Service
public class EpisodeServiceImpl extends ServiceImpl<EpisodeMapper, Episode> implements IEpisodeService {

}
