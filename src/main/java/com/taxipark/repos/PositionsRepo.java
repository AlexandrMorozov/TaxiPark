package com.taxipark.repos;

import com.taxipark.dbmodel.Positions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionsRepo extends CrudRepository<Positions,Integer>
{
    Positions findByPositionName(String positionName);
    List<Positions> findAll();
}
