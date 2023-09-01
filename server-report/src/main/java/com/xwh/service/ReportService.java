package com.xwh.service;

import com.xwh.pojo.vo.OrderReportVO;
import com.xwh.pojo.vo.SalesTop10ReportVO;
import com.xwh.pojo.vo.TurnoverReportVO;
import com.xwh.pojo.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
public interface ReportService {
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

    void exportBusinessData(HttpServletResponse response);

    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);
}
