package com.qiutian.middleware.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author qiutian
 * @date 2020/9/9
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ThsValuationResDTO implements Serializable {
    private static final long serialVersionUID = 8364637490646190131L;
    /**
     * 同花顺返回码
     */
    private Integer errorcode;

    /**
     * 同花顺返回结果
     */
    private String errmsg;

    /**
     * 耗时
     */
    private Integer perf;

    /**
     * 返回数据的数量
     */
    private Integer dataVol;

    /**
     * 同花顺返回数据
     */
    private List<ValuationTablesResDTO> tables;

}
