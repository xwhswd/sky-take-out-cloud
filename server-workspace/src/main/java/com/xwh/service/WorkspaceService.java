package com.xwh.service;

import com.xwh.pojo.vo.BusinessDataVO;
import com.xwh.pojo.vo.DishOverViewVO;
import com.xwh.pojo.vo.OrderOverViewVO;
import com.xwh.pojo.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

/**
*@author xwh
*@version 1.0
* 2023/7/29
*/
public interface WorkspaceService {
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    OrderOverViewVO getOrderOverView();

    SetmealOverViewVO getSetmealOverView();

    DishOverViewVO getDishOverView();
}
