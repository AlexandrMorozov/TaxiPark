package com.taxipark.repos;

import com.taxipark.dbmodel.Arrival_Time;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Arrival_TimeRepo extends CrudRepository<Arrival_Time,Integer>
{
    List<Arrival_Time> findAllByRouteIDAndBusStopIDAndTypeOfDayAndDirection(int route, int stop, String day, String dir);
}
