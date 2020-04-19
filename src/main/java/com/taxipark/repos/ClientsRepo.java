package com.taxipark.repos;

import com.taxipark.dbmodel.Clients;
import org.springframework.data.repository.CrudRepository;

public interface ClientsRepo extends CrudRepository<Clients,Integer>
{
    Clients findByClientLogin(String clientLogin);
    Clients findByClientLoginAndClientPassword(String login, String password);
}
