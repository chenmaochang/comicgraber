package org.cmc.comicgrab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 章节
 * </p>
 *
 * @author cmc
 * @since 2019-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="episode_",resultMap="baseResult")
public class Episode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 章节
     */
    @TableField("index_")
    private Integer index;
    
    /**
     * 章节名
     */
    @TableField("index_name_")
    private String indexName;

    /**
     * bookid
     */
    @TableField("book_id_")
    private Integer bookId;

    /**
     * 书名
     */
    @TableField("book_name_")
    private String bookName;
    
    @TableField(exist=false)
    private List<Page> pages=new ArrayList<>();

}
