package com.exampracticelala.core.Service;

import com.exampracticelala.core.Domain.Actor;

import java.util.List;

public interface ActorService {
    List<Actor> getAllActors();
    List<Actor> getAllAvailableActors();

//    Actor saveAddress(String county, String city);
//
//    Actor updateAddress(Long id, String county, String city);
//
//    List<Actor> getPaginated(int pageNumber);
////    Page<Actor> getPaginated(int pageNumber);
//
//    void deleteAddress(Long addressId);
//
//    List<Actor> getSorted();
}
