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
 * 菜单角色权限表
 * </p>
 *
 * @author cmc
 * @since 2019-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role_permission_relation_")
public class RolePermissionRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单url
     */
    @TableField("menu_url_")
    private String menuUrl;
    /**
     * 菜单id
     */
    @TableField("menu_id_")
    private Integer menuId;
    /**
     * 权限表主键
     */
    @TableField("role_id_")
    private Integer roleId;

    /**
     * 角色名
     */
    @TableField("role_name_")
    private String roleName;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private LocalDateTime createTime;

    @TableField("update_time_")
    private LocalDateTime updateTime;

    /**
     * 系统状态
     */
    @TableField("sys_status_")
    private String sysStatus;


}
