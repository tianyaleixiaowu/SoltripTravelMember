package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

import java.util.List;

/**
 * 查询差标口返回类
 */
@Data
public class APITripsTandardResult extends BusResult {

    public APITripsTandardResult(){}

    public APITripsTandardResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }
    /// <summary>
    /// 火车舱位
    /// </summary>
    private String trainLevel;

    /// <summary>
    /// 飞机舱位
    /// </summary>
    private String flightLevel;

    /// <summary>
    /// 住宿费上限列表
    /// </summary>
    private List<HotelFee> hotelFeeList;
}
