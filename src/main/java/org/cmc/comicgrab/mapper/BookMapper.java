package org.cmc.comicgrab.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.cmc.comicgrab.entity.Book;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * <p>
 * 书 Mapper 接口
 * </p>
 *
 * @author cmc
 * @since 2019-09-26
 */
public interface BookMapper extends BaseMapper<Book> {
	public List<Book> getBook(@Param(Constants.WRAPPER)QueryWrapper<Book> wrapper);
}
