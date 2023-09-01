package com.xwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.context.BaseContext;
import com.xwh.mapper.AddressBookMapper;
import com.xwh.pojo.AddressBook;
import com.xwh.service.AddressBookService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Resource
    private AddressBookMapper addressBookMapper;


    @Override
    public List<AddressBook> listByUser(Long openid) {
        QueryWrapper<AddressBook> queryWrapper = new QueryWrapper<>();
        List<AddressBook> addressBooks = addressBookMapper.selectList(queryWrapper);
        return addressBooks;
    }

    @Override
    public boolean save(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        int insert = addressBookMapper.insert(addressBook);
        return insert>0;
    }
    @Override
    public AddressBook getById(Long id) {
        QueryWrapper<AddressBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        AddressBook addressBook = addressBookMapper.selectOne(queryWrapper);
        return addressBook;
    }

    @Override
    public boolean update(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        int i = addressBookMapper.updateById(addressBook);
        return i>0;
    }

    @Override
    public boolean setDefault(AddressBook addressBook) {
        //1、将当前用户的所有地址修改为非默认地址 update address_book set is_default = ? where user_id = ?
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateIsDefault(0,BaseContext.getCurrentId());
        //2、将当前地址改为默认地址 update address_book set is_default = ? where id = ?
        addressBook.setIsDefault(1);
        return addressBookMapper.updateById(addressBook)>0;
    }

    @Override
    public boolean deleteById(Long id) {
        return addressBookMapper.deleteById(id)>0;
    }

    @Override
    public AddressBook getDefault(String openid) {
        QueryWrapper<AddressBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",openid);
        queryWrapper.eq("isDefault",1);
        return addressBookMapper.selectOne(queryWrapper);
    }


}
