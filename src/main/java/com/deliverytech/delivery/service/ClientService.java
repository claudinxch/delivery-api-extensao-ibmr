package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Client;
import com.deliverytech.delivery.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClientService {
    @Autowired // dependency injection, it tells Spring to automatically provide the required bean (object) instead of you creating it manually with new.
    private ClientRepository clientRepository;

    public Client register(Client client) {
        if(clientRepository.existsByEmail(client.getEmail())){
            throw new IllegalArgumentException("Email already registered: " + client.getEmail());
        }

        validateClientData(client);

        client.setActive(true);

        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Optional<Client> findById(UUID id) {
        return clientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Client> findByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Client> listActive() {
        return clientRepository.findByActiveTrue();
    }

    public Client update(UUID id, Client updatedClient) {
        Client client = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));

        if(!client.getEmail().equals(updatedClient.getEmail()) &&
                clientRepository.existsByEmail(updatedClient.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + updatedClient.getEmail());
        }

        client.setName(updatedClient.getName());
        client.setEmail(updatedClient.getEmail());
        client.setPhoneNumber(updatedClient.getPhoneNumber());
        client.setAddress(updatedClient.getAddress());

        return clientRepository.save(client);
    }

    public void deactivate(UUID id) {
        Client client = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));

        client.deactivate();
        clientRepository.save(client);
    }

    private void validateClientData(Client client) {
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory.");
        }

        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is mandatory");
        }

        if (client.getName().length() < 2) {
            throw new IllegalArgumentException("Name needs at least 2 characters");
        }
    }
}
