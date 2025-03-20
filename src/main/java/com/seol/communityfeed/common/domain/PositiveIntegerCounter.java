package com.seol.communityfeed.common.domain;

public class PositiveIntegerCounter {

    private int count;
    private final int maxLimit;

    public PositiveIntegerCounter() {
        this(1000); // 기본 최대값 1000
    }

    public PositiveIntegerCounter(int maxLimit) {
        if (maxLimit <= 0) {
            throw new IllegalArgumentException("최대 카운트는 0보다 커야 합니다.");
        }
        this.count = 0;
        this.maxLimit = maxLimit;
    }

    public synchronized void increase() {
        if (count < maxLimit) {
            this.count++;
        } else {
            throw new IllegalStateException("최대 카운트를 초과할 수 없습니다.");
        }
    }

    public synchronized void decrease() {
        if (count <= 0) {
            throw new IllegalStateException("카운트는 0 이하로 감소할 수 없습니다.");
        }
        this.count--;
    }

    public synchronized int getCount() {
        return count;
    }
}