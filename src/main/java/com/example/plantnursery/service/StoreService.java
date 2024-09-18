package com.example.plantnursery.service;

import com.example.plantnursery.model.Store;
import com.example.plantnursery.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(Long storeId) {
        return storeRepository.findById(storeId);
    }

    public Store addStore(Store store) {
        return storeRepository.save(store);
    }

    public Store updateStore(Long storeId, Store storeDetails) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        store.setStoreName(storeDetails.getStoreName());
        store.setAddress(storeDetails.getAddress());
        return storeRepository.save(store);
    }

    public void deleteStore(Long storeId) {
        storeRepository.deleteById(storeId);
    }
}

