package org.cmc.comicgrab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 用户部门关系表
 * </p>
 *
 * @author cmc
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_ministries_")
public class UserMinistries implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id_")
    private Integer userId;

    /**
     * 用户姓名
     */
    @TableField("user_name_")
    private String userName;

    /**
     * 部门id
     */
    @TableField("ministries_id_")
    private Integer ministriesId;

    /**
     * 部门名字
     */
    @TableField("ministries_name_")
    private String ministriesName;

    /**
     * 创建者id
     */
    @TableField("create_id_")
    private Integer createId;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private LocalDateTime createTime;


}
