package org.cmc.comicgrab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.cmc.comicgrab.entity.Message;
import org.cmc.comicgrab.mapper.MessageMapper;
import org.cmc.comicgrab.service.IMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-09-04
 */
@Service
@Transactional
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
