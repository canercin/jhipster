package com.jhipster.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PeopleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static People getPeopleSample1() {
        return new People().id(1L).firstname("firstname1").lastname("lastname1");
    }

    public static People getPeopleSample2() {
        return new People().id(2L).firstname("firstname2").lastname("lastname2");
    }

    public static People getPeopleRandomSampleGenerator() {
        return new People().id(longCount.incrementAndGet()).firstname(UUID.randomUUID().toString()).lastname(UUID.randomUUID().toString());
    }
}
