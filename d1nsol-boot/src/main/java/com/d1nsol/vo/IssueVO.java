package com.d1nsol.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class IssueVO {

    // SELECT CASE WHEN NULLABLE='N' AND (DATA_TYPE='VARCHAR2' OR DATA_TYPE='CHAR')
    //     THEN '@NotBlank'
    //     WHEN NULLABLE='N' AND DATA_TYPE = 'NUMBER' THEN '@NotNull @Min(0)'
    //     ELSE '' END
    //     || DECODE(DATA_TYPE, 'NUMBER', '', '@Size(max='||DATA_LENGTH||')')     
    //     ||  ' private '||
    //     DECODE( DATA_TYPE , 'NUMBER', 'Integer ', 'String ' )||
    //     LOWER(COLUMN_NAME)||';'
    // FROM COLS
    // WHERE TABLE_NAME = 'ISSUE'
    // ORDER BY COLUMN_ID;

    @NotNull @Min(0) private Integer issue_sid;
    @NotNull @Min(0) private Integer proj_no;
    @NotBlank@Size(max=200) private String issue_title;
    @NotBlank@Size(max=1) private String status;
    @Size(max=20) private String issue_create_date;
    @Size(max=20) private String issue_start_date;
    @Size(max=20) private String issue_end_date;
}
