package com.qiutian.middleware.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by qiutian on 2020/9/9.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ValuationTableResDTO {
    /**
     * 实时估值
     */
    private List<String> real_time_valuation;
}
