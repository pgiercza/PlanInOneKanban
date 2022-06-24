package pl.edu.wszib.planinonekanban.service.common;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerators {

    public interface IdGenerator<ID> {
        ID next();
    }

    public static class IntIdGenerator implements IdGenerator<Integer> {

        private AtomicInteger atomicInteger = new AtomicInteger(0);

        @Override
        public Integer next() {
            return atomicInteger.getAndIncrement();
        }
    }

    public static class LongIdGenerator implements IdGenerator<Long> {

        private AtomicLong atomicLong = new AtomicLong(0);

        @Override
        public Long next() {
            return atomicLong.getAndIncrement();
        }
    }

    public static class StringIdGenerator implements IdGenerator<String> {
        @Override
        public String next() {
            return UUID.randomUUID().toString();
        }
    }

}
