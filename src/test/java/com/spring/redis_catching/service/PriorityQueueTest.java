package com.spring.redis_catching.service;

import com.spring.redis_catching.BaseTest;
import com.spring.redis_catching.dto.Category;
import com.spring.redis_catching.dto.UserOrder;
import com.spring.redis_catching.helper.PriorityQueue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class PriorityQueueTest extends BaseTest {

    private PriorityQueue priorityQueue;

    @BeforeAll
    public void setupQueue() {
        RScoredSortedSetReactive<UserOrder> sortedSet = this.client.getScoredSortedSet("user:order:queue", new TypedJsonJacksonCodec(UserOrder.class));
        this.priorityQueue = new PriorityQueue(sortedSet);
    }

    @Test
    public void producer() {
        Flux.interval(Duration.ofSeconds(1))
                .map(l -> (l.intValue() * 5))
                .doOnNext(i -> {
                    UserOrder u1 = new UserOrder();
                    u1.setId(i + 1);
                    u1.setCategory(Category.GUEST);
                    UserOrder u2 = new UserOrder();
                    u2.setId(i + 2);
                    u2.setCategory(Category.STD);
                    UserOrder u3 = new UserOrder();
                    u3.setId(i + 3);
                    u3.setCategory(Category.PRIME);
                    UserOrder u4 = new UserOrder();
                    u4.setId(i + 4);
                    u4.setCategory(Category.STD);
                    UserOrder u5 = new UserOrder();
                    u5.setId(i + 5);
                    u5.setCategory(Category.GUEST);
                    Mono<Void> mono = Flux.just(u1, u2, u3, u4, u5)
                            .flatMap(this.priorityQueue::add)
                            .then();
                    StepVerifier.create(mono)
                            .verifyComplete();
                }).subscribe();
        sleep(60_000);
    }

    @Test
    public void consumer() {
        this.priorityQueue.takeItems()
                .delayElements(Duration.ofMillis(500))
                .doOnNext(System.out::println)
                .subscribe();
        sleep(60_000);
    }
}
