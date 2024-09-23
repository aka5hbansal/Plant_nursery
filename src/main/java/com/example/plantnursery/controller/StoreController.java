package com.example.plantnursery.controller;

import com.example.plantnursery.model.Store;
import com.example.plantnursery.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/api/stores")
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/api/stores/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Optional<Store> store = storeService.getStoreById(id);
        return store.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/staff/stores/add-store")
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store newStore = storeService.addStore(store);
        return ResponseEntity.ok(newStore);
    }

    @PutMapping("/api/staff/stores/update-store/{id}")
    public ResponseEntity<Store> updateStore(
            @PathVariable Long id,
            @RequestBody Store storeDetails) {
        Store updatedStore = storeService.updateStore(id, storeDetails);
        return ResponseEntity.ok(updatedStore);
    }

    @DeleteMapping("/api/staff/stores/delete-store/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
}

