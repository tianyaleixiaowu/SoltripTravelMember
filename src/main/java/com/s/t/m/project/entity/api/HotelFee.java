package com.s.t.m.project.entity.api;

import lombok.Data;

/**
 * 住宿费标准
 */
@Data
public class HotelFee {

    /// <summary>
    /// 城市ID
    /// </summary>
    private String cityID;

    /// <summary>
    /// 住宿标准上限（不限传-1）
    /// </summary>
    private int hotelFeeValue;

    /// <summary>
    /// 币种三字码（CNY：人民币）
    /// </summary>
    private String currency;
}
