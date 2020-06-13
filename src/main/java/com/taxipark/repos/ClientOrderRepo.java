package com.taxipark.repos;

import com.taxipark.dbmodel.ClientOrder;
import com.taxipark.dbmodel.Personnel;
import com.taxipark.dto.StatisticsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientOrderRepo extends CrudRepository<ClientOrder,Integer>
{
    //ClientOrder f
   //Find all active orders of selected type for the selected day
    @Query("SELECT new com.taxipark.dbmodel.ClientOrder(o.orderedService," +
            "o.dateOfOrder,o.timeOfOrder) FROM ClientOrder o WHERE " +
            "dateOfOrder=:orderDate AND o.orderType=:typeOrder AND" +
            " o.status=:status ORDER BY o.timeOfOrder ASC")
    List<ClientOrder> findTimesOfAllActiveOrdersByDay(@Param("orderDate") String orderDate,
                                                      @Param("typeOrder")String orderType,
                                                      @Param("status")String status);

    //Find number of all orders of selected type for current week, that belongs to the selected authorized client
   @Query("SELECT COUNT(o.clientID) FROM ClientOrder o WHERE " +
            "o.clientID=:clientID AND o.orderType=:orderType AND" +
           " o.dateOfOrder BETWEEN :frm AND :to")
   int findNumOfClientOrdersPerWeek(@Param("clientID") int clientID,
                                    @Param("orderType") String type,
                                    @Param("frm") String from,
                                    @Param("to") String to );
    //Find number of all orders of selected type for current week, that belongs to the selected unauthorized client
   @Query("SELECT COUNT(o.unauthContactInfo) FROM ClientOrder o WHERE " +
           "o.unauthContactInfo=:unauthInfo AND o.orderType=:orderType AND" +
           " o.dateOfOrder BETWEEN :frm AND :to")
   int findNumOfClientOrdersPerWeek(@Param("unauthInfo") String unauthInfo,
                                    @Param("orderType") String type,
                                    @Param("frm") String from,
                                    @Param("to") String to );
    //Find number of all active orders of selected type, that belongs to the selected to the selected authorized client
   @Query("SELECT COUNT(o.clientID) FROM ClientOrder o WHERE " +
           "o.clientID=:clientID AND o.orderType=:typeOrder AND" +
           " o.status=:status")
   int findNumOfActiveClientOrders(@Param("clientID") int clientID,
                                   @Param("typeOrder")String orderType,
                                   @Param("status") String status);

    //Find number of all active orders of selected type, that belongs to the selected to the selected unauthorized client
   @Query("SELECT COUNT(o.unauthContactInfo) FROM ClientOrder o WHERE" +
           " o.unauthContactInfo=:unauthInfo AND o.orderType=:typeOrder" +
           " AND o.status=:status")
   int findNumOfActiveClientOrders(@Param("unauthInfo") String unauthInfo,
                                   @Param("typeOrder")String orderType,
                                   @Param("status") String status);

    ClientOrder findByOrderID(int orderID);
    List<ClientOrder> findByAssignedEmployeeAndStatus(Personnel personnel, String status);
    List<ClientOrder> findByClientIDAndOrderTypeAndStatusOrderByDateOfOrderAsc(Integer clientID,String orderType,String status);

}
