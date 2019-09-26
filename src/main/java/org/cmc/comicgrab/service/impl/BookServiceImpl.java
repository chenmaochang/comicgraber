package org.cmc.comicgrab.service.impl;

import org.cmc.comicgrab.entity.Book;
import org.cmc.comicgrab.mapper.BookMapper;
import org.cmc.comicgrab.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
