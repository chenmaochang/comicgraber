package org.cmc.comicgrab.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 书
 * </p>
 *
 * @author cmc
 * @since 2019-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="book_",resultMap="baseResult")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id_", type = IdType.AUTO)
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
     * 封面路径
     */
    @TableField("cover_src_")
    private String coverSrc;

    /**
     * 封面图片 如abc.jpg
     */
    @TableField("cover_picture_")
    private String coverPicture;

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
    private Integer sourceId;

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

    @TableField(exist=false)
    private List<Page> pages;

}
