package com.xwh.controller;

import com.xwh.pojo.result.Result;
import com.xwh.pojo.vo.OrderReportVO;
import com.xwh.pojo.vo.SalesTop10ReportVO;
import com.xwh.pojo.vo.TurnoverReportVO;
import com.xwh.pojo.vo.UserReportVO;
import com.xwh.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/report")
@Api("统计信息接口")
@Slf4j
public class ReportController {
    @Resource
    private ReportService reportService;

    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("----营业额数据统计:",begin,end+"----");
        return Result.success(reportService.getTurnoverStatistics(begin,end));
    }

    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("----用户数据统计:",begin,end+"----");
        return Result.success(reportService.getUserStatistics(begin,end));
    }

    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计")
    public Result<OrderReportVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("----订单数据统计:",begin,end+"----");
        return Result.success(reportService.getOrderStatistics(begin,end));
    }

    @GetMapping("/top10")
    @ApiOperation("销量排名top10")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("----销量排名top10:",begin,end+"----");
        return Result.success(reportService.getSalesTop10(begin,end));
    }

    @GetMapping("/export")
    @ApiOperation("导出运营数据报表")
    public void export(HttpServletResponse response){
        log.info("----导出运营数据报表----");
        reportService.exportBusinessData(response);
    }
}
