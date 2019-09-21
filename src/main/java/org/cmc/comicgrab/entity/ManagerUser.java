package org.cmc.comicgrab.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("manager_user_")
public class ManagerUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增长id
     */
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    @TableField("name_")
    private String name;

    /**
     * 密码
     */
    @TableField("password_")
    private String password;

    /**
     * 身份证
     */
    @TableField("identity_")
    private String identity;

    /**
     * 联系电话
     */
    @TableField("phone_")
    private String phone;

    /**
     * 账号
     */
    @TableField("account_")
    private String account;

    /**
     * 创建时间
     */
    @TableField("create_time_")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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

    //以下为备用字段
    @TableField(exist=false)
    private String spareStr;
    @TableField(exist=false)
    private Integer spareInt;
    @TableField(exist=false)
    private LocalDateTime spareDate;
}
