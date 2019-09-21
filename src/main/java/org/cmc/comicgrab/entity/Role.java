package org.cmc.comicgrab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role_")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增长
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名
     */
    @TableField("role_name_")
    private String roleName;

    /**
     * 角色等级
     */
    @TableField("role_level_")
    private Integer roleLevel;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private LocalDateTime createTime;

    /**
     * 系统状态
     */
    @TableField("sys_status_")
    private String sysStatus;


}
