package com.xwh.service;

import com.xwh.pojo.AddressBook;

import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
public interface AddressBookService {
    /**
     * 展示当前用户下所有地址
     * @param openid
     * @return
     */
    List<AddressBook> listByUser(Long openid);

    /**
     * 保存地址
     * @param addressBook
     * @return
     */
    public boolean save(AddressBook addressBook);

    /**
     * 根据地址id展示地址
     * @param id
     * @return
     */
    AddressBook getById(Long id);

    /**
     * 根据id修改地址
     * @param addressBook
     * @return
     */
    boolean update(AddressBook addressBook);

    /**
     * 设置地址是否为默认地址
     * @param addressBook
     * @return
     */
    boolean setDefault(AddressBook addressBook);

    /**
     * 根据id删除地址信息
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 获取当前用户的默认地址
     * @param openid
     * @return
     */
    AddressBook getDefault(String openid);
}
