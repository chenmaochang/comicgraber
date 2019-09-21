package org.cmc.comicgrab.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author cmc
 * @since 2019-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("message_")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息标题
     */
    @TableField("title_")
    private String title;

    /**
     * 消息内容
     */
    @TableField("content_")
    private String content;

    /**
     * 消息类别
     */
    @TableField("message_type_")
    private String messageType;

    /**
     * 创建者id
     */
    @TableField("create_id_")
    private Integer createId;

    /**
     * 创建者名称
     */
    @TableField("create_name_")
    private String createName;

    /**
     * 接收者id
     */
    @TableField("receiver_id_")
    private Integer receiverId;

    /**
     * 接收者名称
     */
    @TableField("receiver_name_")
    private String receiverName;

    /**
     * 系统状态
     */
    @TableField("sys_status_")
    private String sysStatus;

    /**
     * 已读未读
     */
    @TableField("read_status_")
    private String readStatus;

    /**
     * 发送状态
     */
    @TableField("send_status_")
    private String sendStatus;

    /**
     * 回复消息
     */
    @TableField("reply_to_")
    private String replyTo;
    /**
     * 创建时间
     */
    @TableField("create_time_")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time_")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
