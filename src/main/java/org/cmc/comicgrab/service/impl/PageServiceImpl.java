package org.cmc.comicgrab.service.impl;

import org.cmc.comicgrab.entity.Page;
import org.cmc.comicgrab.mapper.PageMapper;
import org.cmc.comicgrab.service.IPageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 每页内容 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-09-27
 */
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements IPageService {

}
