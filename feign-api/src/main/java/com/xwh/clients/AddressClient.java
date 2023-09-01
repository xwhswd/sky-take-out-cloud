package com.xwh.clients;

import com.xwh.pojo.AddressBook;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/7
 */
@FeignClient(value = "client-address")
public interface AddressClient {
    @PostMapping("/addressBook/geyById/{addressBookId}")
    AddressBook getAddressById(@PathVariable("addressBookId") Long addressBookId);
}
