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
 * 通用关系表
 * </p>
 *
 * @author cmc
 * @since 2019-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("common_relation_")
public class CommonRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 关系名称
     */
    @TableField("relation_type_")
    private String relationType;

    /**
     * 主关系方
     */
    @TableField("master_id_")
    private Integer masterId;

    /**
     * 从关系方
     */
    @TableField("slave_id_")
    private Integer slaveId;

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


}
