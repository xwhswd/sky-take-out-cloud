package com.xwh.controller;

import com.xwh.context.BaseContext;
import com.xwh.pojo.AddressBook;
import com.xwh.pojo.result.Result;
import com.xwh.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;



/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
@Api("地址簿操作接口")
public class AddressBookController{
    @Resource
    private AddressBookService addressBookService;
    @Resource
    RedisTemplate redisTemplate;
    @ModelAttribute
    public void setBaseContext(HttpServletRequest request){
        BaseContext.setCurrentId((Long) redisTemplate.opsForValue().get("jwt_"+request.getHeader("token")));
    }

    @GetMapping("/list")
    @ApiOperation("查询当前用户地址信息")
    public Result<List<AddressBook>> list() {
        log.info("----正在查询当前用户信息:"+BaseContext.getCurrentId()+"----");
        List<AddressBook> list = addressBookService.listByUser(BaseContext.getCurrentId());
        return Result.success(list);
    }

    @PostMapping
    @ApiOperation("新增用户地址信息")
    public Result save(@RequestBody AddressBook addressBook) {
        log.info("----正在新增用户地址信息"+BaseContext.getCurrentId()+":"+addressBook+"----");
        addressBookService.save(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据地址id获取地址具体信息")
    public Result<AddressBook> getById(@PathVariable Long id) {
        log.info("----正在获取用户地址信息"+BaseContext.getCurrentId()+":"+id+"----");
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @PutMapping
    @ApiOperation("根据地址id修改地址信息")
    public Result update(@RequestBody AddressBook addressBook) {
        log.info("----正在用户用户地址信息"+BaseContext.getCurrentId()+":"+addressBook+"----");
        addressBookService.update(addressBook);
        return Result.success();
    }

    @PutMapping("/default")
    @ApiOperation("根据id设置为默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        log.info("----设置用户默认地址"+BaseContext.getCurrentId()+":"+addressBook+"----");
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除用户地址信息")
    public Result deleteById(Long id) {
        log.info("----正在删除用户地址信息"+BaseContext.getCurrentId()+":"+id+"----");
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault() {
        log.info("----正在获取用户默认地址信息"+BaseContext.getCurrentId()+"----");
        AddressBook aDefault = addressBookService.getDefault(String.valueOf(BaseContext.getCurrentId()));
        if (aDefault!=null) {
            return Result.success(aDefault);
        }
        return Result.error("没有查询到默认地址");
    }

    @PostMapping("/geyById/{addressBookId}")
    AddressBook getAddressById(@PathVariable("addressBookId") Long addressBookId){
        return addressBookService.getById(addressBookId);
    }
}
