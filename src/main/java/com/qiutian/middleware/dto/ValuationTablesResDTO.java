package com.qiutian.middleware.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Created by qiutian on 2020/9/9.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ValuationTablesResDTO {
    /**
     * 同花顺基金代码
     */
    private String thscode;

    /**
     * 估值时间点
     */
    private List<Date> time;

    private ValuationTableResDTO table;
}
