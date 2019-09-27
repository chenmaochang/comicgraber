package org.cmc.comicgrab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 每页内容
 * </p>
 *
 * @author cmc
 * @since 2019-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="page_")
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_", type = IdType.AUTO)
    private Long id;

    /**
     * 页码目录
     */
    @TableField("page_index_")
    private Integer pageIndex;

    /**
     * 页地址
     */
    @TableField("page_url_")
    private String pageUrl;
    
    /**
     * 集数
     */
    @TableField("episode_")
    private String episode;

    /**
     * 存放路径
     */
    @TableField("page_src_")
    private String pageSrc;

    /**
     * 书籍id_
     */
    @TableField("book_id_")
    private Integer bookId;

    /**
     * 书名
     */
    @TableField("book_title_")
    private String bookTitle;

    /**
     * 内容
     */
    @TableField("page_content_")
    private String pageContent;


}
