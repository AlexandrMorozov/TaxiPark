package com.taxipark.repos;

import com.taxipark.dto.RpBsDto;
import com.taxipark.dbmodel.Route_Points;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Route_PointsRepo extends CrudRepository<Route_Points,Integer>
{
    //List<Route_Points> findByRouteIDAndDirection(int routeID, String direction);

    @Query("SELECT new com.taxipark.dto.RpBsDto( e.busStopID ,e.routeID, e.direction, e.stopNumber, d.name) "
            + "FROM Bus_Stop d INNER JOIN Route_Points e ON d.stopID=e.busStopID WHERE e.direction=:direction" +
            " AND e.routeID=:routeID ORDER BY e.stopNumber ASC")
    List<RpBsDto> fetchRpBsDataInnerJoin(@Param("routeID") int route,@Param("direction") String direction);
}
