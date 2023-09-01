package com.xwh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.pojo.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
    @Update(" update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefault(@Param("isDefault") int isDefault, @Param("userId") Long userId);
}
