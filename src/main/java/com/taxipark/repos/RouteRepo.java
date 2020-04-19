package com.taxipark.repos;

import com.taxipark.dbmodel.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RouteRepo extends CrudRepository<Route,Integer>
{
    List<Route> findByType(String type);
}
