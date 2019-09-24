package org.cmc.comicgrab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单-三共平台
 * </p>
 *
 * @author cmc
 * @since 2019-08-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("menu_")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增长id
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名
     */
    @TableField("name_")
    private String name;

    /**
     * 菜单父id
     */
    @TableField("parent_id_")
    private Integer parentId;

    /**
     * 菜单链接
     */
    @TableField("menu_url_")
    private String menuUrl;
    
    /**
     * 请求类型
     */
    @TableField("request_type_")
    private String requestType;
    
    /**
     * 菜单类型
     */
    @TableField("menu_type_")
    private String menuType;

    /**
     * 菜单图标
     */
    @TableField("menu_icon_")
    private String menuIcon;

    /**
     * 系统状态
     */
    @TableField("sys_status_")
    private String sysStatus;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private LocalDateTime createTime;

    @TableField(exist=false)
    List<Menu> children;

}
