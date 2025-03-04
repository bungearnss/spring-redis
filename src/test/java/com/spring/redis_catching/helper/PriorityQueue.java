package com.spring.redis_catching.helper;

import com.spring.redis_catching.dto.Category;
import com.spring.redis_catching.dto.UserOrder;
import org.redisson.api.RScoredSortedSetReactive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PriorityQueue {

    private RScoredSortedSetReactive<UserOrder> queue;

    public PriorityQueue(RScoredSortedSetReactive<UserOrder> queue) {
        this.queue = queue;
    }

    public Mono<Void> add(UserOrder userOrder) {
        return this.queue.add(
                userOrder.getCategory().ordinal(),
                userOrder
        ).then();
    }

    public Flux<UserOrder> takeItems() {
        return this.queue.takeFirstElements()
                .limitRate(1);
    }

    private double getScore(Category category) {
        return category.ordinal() + Double.parseDouble("0." + System.nanoTime());
    }
}
