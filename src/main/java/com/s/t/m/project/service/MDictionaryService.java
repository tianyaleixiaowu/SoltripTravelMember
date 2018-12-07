package com.s.t.m.project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.s.t.m.common.redis.RedisUtils;
import com.s.t.m.common.utils.ObjectUtils;
import com.s.t.m.project.dao.MDictionaryMapper;
import com.s.t.m.project.entity.MDictionary;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 字典
 *
 * @author Bai
 * @date 2018/11/24 16:13
 */
@Service
@Slf4j
public class MDictionaryService {

    @Autowired
    private MDictionaryMapper mDictionaryMapper;

    @Resource
    private RedisUtils redisService;

    private static String divKey = "DictionaryInit";

    /**
     * 初始化字典数据
     *
     * @return
     */
    public List<MDictionary> initDictionaryData() {
        try {
            List<MDictionary> list = mDictionaryMapper.getAllDictionary();
            if (list != null && list.size() > 0) {
                boolean b = redisService.setList(divKey, list);
                if (b)
                    log.info("数据字典始化成功！");
            } else {
                log.error("数据字典初始化失败，数据库不包含任何数据！");
            }
            return list;
        } catch (Exception e) {
            log.error("异常方法 => initDictionaryData", e);
            return null;
        }
    }

    /**
     * 根据组ID获取数据字典
     *
     * @param groupID
     * @return
     */
    public List<MDictionary> getDictionaryByGroupID(int groupID) {
        try {
            List<MDictionary> list = redisService.getList(divKey, MDictionary.class);
            if (list == null)//没有缓存数据重新加载
            {
                list = initDictionaryData();
            }
            List<MDictionary> groupList = new ArrayList<>();
            //TODO 数据量大进行优化
            for (int i = 0; i < list.size(); i++) {
                MDictionary m = list.get(i);
                if (m.equals(groupID))
                    groupList.add(m);
            }
            if (groupList != null && groupList.size() > 0) {
                return groupList;
            } else {
                log.error("组ID:" + groupID + " 没有查询到对应的字典数据!");
            }
        } catch (Exception e) {
            log.error("异常方法 => getDictionaryByGroupID", e);
        }
        return new ArrayList<>();
    }

    /**
     * 根据Key获取字典名称
     *
     * @param groupID 组ID
     * @param dKey    字典键
     * @return
     */
    public String getDictionaryDValue(int groupID, String dKey) {
        try {
            List<MDictionary> list = redisService.getList(divKey, MDictionary.class);
            if (list == null)//没有缓存数据重新加载
            {
                list = initDictionaryData();
            }
            //TODO 数据量大进行优化
            String dValue = "";
            for (int i = 0; i < list.size(); i++) {
                MDictionary m = list.get(i);
                if (m.getGroupid() == groupID && m.getDkey().equals(dKey)) {
                    dValue = m.getDvalue();
                    break;
                }
            }
            if (!ObjectUtils.isEmpty(dValue)) {
                return dValue;
            } else {
                log.error("组ID:" + groupID + " Key：" + dKey + " 没有查询到对应的字典数据!");
            }
        } catch (Exception e) {
            log.error("异常方法 => getDictionaryDValue", e);
        }
        return "";
    }


}