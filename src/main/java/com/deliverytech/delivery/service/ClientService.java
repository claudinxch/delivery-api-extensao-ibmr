package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    public Client register(Client client) {
        return new Client();
    }

    public List<Client> listClients() {
        return new ArrayList<>();
    }

}
