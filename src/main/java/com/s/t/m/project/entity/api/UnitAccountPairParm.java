package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

import java.util.List;

/**
 *获取帐套编号接参实体
 */
@Data
public class UnitAccountPairParm extends SafetyParm{
    private List<String> unitIDList;
}
