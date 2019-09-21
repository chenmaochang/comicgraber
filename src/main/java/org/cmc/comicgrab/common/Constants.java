package org.cmc.comicgrab.common;

public class Constants {
	public enum Dict {
		GENDER(1, "性别"), RACE(2, "民族"), RELATION(3, "与户主关系"), HEALTH(4, "健康状况"), HOUSEHOLD(5, "户口性质"), FAITH(6, "宗教信仰"), CERTIFICATE(7, "证件类型"), JOB_STATUS(8, "工作状况"), MARRIAGE(9, "婚姻状况"), OCCUPATION(10, "职业"), POLICITAL_STATUS(11, "政治面貌"), NATION(12, "国籍"), EDUCATION(13, "文化程度"), RESIDENCE(14, "住所性质"), RENT_TYPE(15, "户类型"), MOBILE_REASON(16, "流动原因"), CHANGE_TYPE(17, "变动类型"), CARE_TYPE(18, "关注类型"), IMPORTANT_TYPE(19, "重点类型"), HOUSE_TYPE(20, "房屋类型"), HOUSE_STRUCTURE(21, "房屋结构"),
		HOUSE_BELONG_TYPE(22, "房屋权属情况"), DEFENSE_SITUATION(23, "三防情况"), PROPERTY_TYPE(24, "产权性质"), HOUSING_TYPE(25, "住房形式"), RENT_USE_TYPE(26, "租赁用途"), LEAVE_REASON(27, "退出原因"), DEPARTMENT(28, "所属机构"), EMPLOYMENT_STATUS(29, "就业情况");

		Dict(Integer type, String description) {
			this.type = type;
			this.description = description;
		}

		private final Integer type;
		private final String description;

		public Integer getType() {
			return type;
		}

		public String getDescription() {
			return description;
		}
	}

	/**
	 * 订单状态
	 * <p>
	 * Company:rayootech
	 * </p>
	 * 
	 * @author zhangxueshen
	 * @date 2016-6-14
	 */
	public enum OrderStats {

		DELETE(0, "删除"), RESERVE(1, "订单预定"), CONFIRM(2, "订单确认"), COMPLETE(3, "订单完成"), CLOSE(4, "订单关闭");

		OrderStats(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		private final Integer value;
		private final String name;

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

	}

	public enum SystemConstants {
		NORMAL("Y", "正常"), FORBIDDEN("N", "禁用"), SUCCESS("SUCCESS", "成功"), MESSAGE("MESSAGE", "消息");
		SystemConstants(String value, String name) {
			this.value = value;
			this.name = name;
		}

		private final String value;
		private final String name;

		public String getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	public enum RelationType {
		MANAGE("MANAGE", "管理"),VICE_MINISTRIES("VICE_MINISTRIES","所属副部门");
		RelationType(String value, String name) {
			this.value = value;
			this.name = name;
		}

		private final String value;
		private final String name;

		public String getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}
}
