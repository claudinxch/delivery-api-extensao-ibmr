package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.entity.Client;
import com.deliverytech.delivery.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired // Dependency injection
    private ClientService clientService;

    //Register new client
    @PostMapping
    public ResponseEntity<?> register(@Validated @RequestBody Client client) {
        try {
            Client savedClient = clientService.register(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping
    public ResponseEntity<List<Client>> list() {
        List<Client> clients = clientService.listActive();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
            Optional<Client> client = clientService.findById(id);

            if(client.isEmpty()) return ResponseEntity.notFound().build();

            return ResponseEntity.ok(client.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable UUID id, @Validated @RequestBody Client client) {
        try {
            Client updatedClient = clientService.update(id, client);
            return ResponseEntity.ok(updatedClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error no servidor");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivate(@PathVariable UUID id) {
        try {
            clientService.deactivate(id);
            return ResponseEntity.ok().body("Client deactivated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Client>> search(@RequestParam String name) {
        List<Client> clients = clientService.findByName(name);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        Optional<Client> client = clientService.findByEmail(email);

        if(client.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(client.get());
    }
}

