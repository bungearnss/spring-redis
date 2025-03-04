package com.spring.redis_catching.service;

import com.spring.redis_catching.BaseTest;
import com.spring.redis_catching.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapCacheTest extends BaseTest {

    @Test
    public void mapCacheTest() {
        // Map<Integer, Student>
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapCacheReactive<Integer, Student> mapCache = this.client.getMapCache("users:cache", codec);

        Student student1 = new Student();
        student1.setName("sam");
        student1.setAge(10);
        student1.setCity("atlanta");
        student1.setMarks(List.of(1, 2, 3));

        Student student2 = new Student();
        student1.setName("jake");
        student1.setAge(30);
        student1.setCity("miami");
        student1.setMarks(List.of(10, 20, 30));

        Mono<Student> st1 = mapCache.put(1, student1, 5, TimeUnit.SECONDS);
        Mono<Student> st2 = mapCache.put(2, student2, 10, TimeUnit.SECONDS);

        StepVerifier.create(st1.then(st2).then())
                .verifyComplete();

        sleep(3000);

        // access students
        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();

        sleep(3000);

        // access students
        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();
    }
}
