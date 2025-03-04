package com.spring.redis_catching.service;

import com.spring.redis_catching.BaseTest;
import com.spring.redis_catching.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

public class KeyValueObjectTest extends BaseTest {

    @Test
    public void keyValueObjectTest() {
        Student student = new Student();
        student.setName("marshal");
        student.setAge(10);
        student.setCity("atlanta");
        student.setMarks(Arrays.asList(1,2, 3));
        RBucketReactive<Student> bucket = this.client.getBucket("student:1", new TypedJsonJacksonCodec(Student.class));
        Mono<Void> set = bucket.set(student);
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
    }
}
