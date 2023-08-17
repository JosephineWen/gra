package com.it.shop.englishShoping.service;
import com.it.shop.englishShoping.domain.Address;

import java.util.List;

public interface AddressService {
    List<Address> getList(String id);

    Address getByUserId( String id,String uid);

    boolean updateById(Address address);

    String addByAddress(Address address);
    boolean deleteById(String id);
}
