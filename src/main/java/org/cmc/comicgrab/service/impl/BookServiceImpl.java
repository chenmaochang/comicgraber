package org.cmc.comicgrab.service.impl;

import javax.annotation.Resource;

import org.cmc.comicgrab.entity.Book;
import org.cmc.comicgrab.mapper.BookMapper;
import org.cmc.comicgrab.service.IBookService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 书 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-09-26
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
@Resource
BookMapper bookMapper;

}
