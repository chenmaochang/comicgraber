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
 * 角色部门关联表
 * </p>
 *
 * @author cmc
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role_ministries_relation_")
public class RoleMinistriesRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色表id
     */
    @TableField("role_id_")
    private Integer roleId;

    /**
     * 角色名
     */
    @TableField("role_name_")
    private String roleName;

    /**
     * 部门id
     */
    @TableField("ministries_id_")
    private Integer ministriesId;

    /**
     * 部门名
     */
    @TableField("ministries_name_")
    private String ministriesName;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private LocalDateTime createTime;

    /**
     * 创建者id
     */
    @TableField("create_id_")
    private Integer createId;


}
