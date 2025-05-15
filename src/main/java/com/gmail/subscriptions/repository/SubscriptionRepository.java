package com.gmail.subscriptions.repository;

import com.gmail.subscriptions.model.Subscription;
import com.gmail.subscriptions.model.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = "SELECT type FROM Subscription GROUP BY type ORDER BY COUNT(*) DESC LIMIT 3", nativeQuery = true)
    List<SubscriptionType> getTopThreeSubs();
}
