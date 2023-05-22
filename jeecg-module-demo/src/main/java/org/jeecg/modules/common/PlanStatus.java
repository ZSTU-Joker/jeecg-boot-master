package org.jeecg.modules.common;

import lombok.Getter;

/**
 * @Author: Linker
 * @Date: 2023/5/16 16:49
 * @Description:
 */
@Getter
public enum PlanStatus {
    CODE_0("0","草稿",""),
    CODE_1("1","待主管审核",""),
    CODE_2("2","主管驳回","个人修改后变为待主管审核"),
    CODE_3("3","变更审核中","主管通过后变成待行政确认"),
    CODE_4("4","待行政确认",""),
    CODE_5("5","行政驳回","个人修改提交后变为待主管审核"),
    CODE_21("21","待自评",""),
    CODE_22("22","待主管评",""),
    CODE_23("23","主管重评","行政驳回，主管重评后变为待行政备案"),
    CODE_24("24","待行政备案",""),
    CODE_25("25","完成",""),
    CODE_26("26","申诉审核",""),
    CODE_27("27","申诉被拒","");

    private String code;
    private String status;
    private String remark;

    PlanStatus(String code, String status, String remark) {
        this.code = code;
        this.status = status;
        this.remark = remark;
    }
}
