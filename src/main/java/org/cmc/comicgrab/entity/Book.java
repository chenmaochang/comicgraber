package org.cmc.comicgrab.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 书
 * </p>
 *
 * @author cmc
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="book_")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId("id_")
    private Integer id;

    /**
     * 书名
     */
    @TableField("book_name_")
    private String bookName;

    /**
     * 作者名
     */
    @TableField("author_")
    private String author;

    /**
     * 封面url
     */
    @TableField("cover_url_")
    private String coverUrl;

    /**
     * 类型
     */
    @TableField("book_type_")
    private String bookType;

    /**
     * 来源网站
     */
    @TableField("source_website_")
    private String sourceWebsite;

    /**
     * 来源网站弱外键id
     */
    @TableField("source_id_")
    private Long sourceId;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private LocalDateTime createTime;

    /**
     * 创建ip
     */
    @TableField("create_ip_")
    private String createIp;

    /**
     * 创建者id
     */
    @TableField("create_id_")
    private Integer createId;

    /**
     * 上传状态
     */
    @TableField("upload_status_")
    private String uploadStatus;


}
