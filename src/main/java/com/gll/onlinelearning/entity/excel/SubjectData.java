package com.gll.onlinelearning.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
    @ExcelProperty(index = 2)
    private String description;
    @ExcelProperty(index = 3)
    private String link;
}
