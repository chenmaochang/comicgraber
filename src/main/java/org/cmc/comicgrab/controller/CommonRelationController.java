package org.cmc.comicgrab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.entity.CommonRelation;
import org.cmc.comicgrab.entity.ManagerUser;
import org.cmc.comicgrab.service.ICommonRelationService;
import org.cmc.comicgrab.service.IManagerUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 * 通用关系表 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-09-09
 */
@RestController
@RequestMapping("/common-relation")
public class CommonRelationController {
	@Resource
	private IManagerUserService managerUserService;
	@Resource
	private ICommonRelationService commonRelationService;

	/**
	 *2019年9月19日 上午11:08:03
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @return
	 */
	@PostMapping("vice-ministries")
	public JSONObject saveViceMinistries(@RequestParam("userId") Integer userId, @RequestParam("ministriesIds") String ministriesIds) {
		JSONObject rtnObj = new JSONObject();
		try {
			JSONArray ministriesArrays = JSONObject.parseArray(ministriesIds);
			List<CommonRelation> commonRelations = new ArrayList<>();
			fetchCommonRelationFromJSONArray(userId, ministriesArrays, commonRelations);
			ManagerUser user = managerUserService.getById(userId);
			QueryWrapper<CommonRelation> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("relation_type_", Constants.RelationType.VICE_MINISTRIES.getValue()).eq("slave_id_", user.getId());
			commonRelationService.remove(queryWrapper);
			commonRelationService.saveBatch(commonRelations);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
			rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), "保存成功");
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), "保存失败:" + e.getCause());
		}
		return rtnObj;
	}

	/**从jsonarray里取出commonRelation放到list里
	 *2019年9月19日 上午11:44:29
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param userId
	 * @param ministriesArrays
	 * @param commonRelations
	 */
	private void fetchCommonRelationFromJSONArray(Integer userId, JSONArray ministriesArrays, List<CommonRelation> commonRelations) {
		ManagerUser user=(ManagerUser) SecurityUtils.getSubject().getPrincipal();;
		ministriesArrays.forEach(commonRelationObj -> {
			CommonRelation commonRelation = new CommonRelation();
			commonRelation.setMasterId(((JSONObject) commonRelationObj).getInteger("value"));
			commonRelation.setSlaveId(userId);
			commonRelation.setRelationType(Constants.RelationType.VICE_MINISTRIES.getValue());
			commonRelation.setCreateId(user.getId());
			commonRelations.add(commonRelation);
		});
	}
}
