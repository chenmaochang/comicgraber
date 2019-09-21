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
 * 部门
 * </p>
 *
 * @author cmc
 * @since 2019-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ministries_")
public class Ministries implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门名称
     */
    @TableField("name_")
    private String name;

    /**
     * 管理优先级数字越小级别越高
     */
    @TableField("level_")
    private Integer level;

    /**
     * 直接上级部门id
     */
    @TableField("parent_id_")
    private Integer parentId;

    /**
     * 路径,  爷id-父id-自己id
     */
    @TableField("path_")
    private String path;

    /**
     * 地址编号
     */
    @TableField("area_code_")
    private String areaCode;

    /**
     * 地址类型
     */
    @TableField("area_type_")
    private String areaType;

    /**
     * 地址信息
     */
    @TableField("address_detail_")
    private String addressDetail;

    /**
     * 是否用工单位
     */
    @TableField("work_unit_")
    private String workUnit;

    /**
     * 类别
     */
    @TableField("type_")
    private String type;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time_")
    private LocalDateTime updateTime;

    /**
     * 创建者id
     */
    @TableField("create_id_")
    private Integer createId;

    /**
     * 系统状态
     */
    @TableField("sys_status_")
    private String sysStatus;

    /**
     * 备注
     */
    @TableField("remarks_")
    private String remarks;

    @TableField(exist=false)
    List<Ministries> children;

}
