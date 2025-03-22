package com.seol.communityfeed.common.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
public class PositiveIntegerCounter {

    private int count;

    @Transient  // 👉 DB에 매핑하지 않음
    private int maxLimit = 1000;

    public PositiveIntegerCounter() {
    }

    public PositiveIntegerCounter(int maxLimit) {
        if (maxLimit <= 0) {
            throw new IllegalArgumentException("최대 카운트는 0보다 커야 합니다.");
        }
        this.count = 0;
        this.maxLimit = maxLimit;
    }

    public PositiveIntegerCounter(int count, int maxLimit) {
        this.count = count;
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
        if (count > 0) {
            count--;
        }
    }

    public synchronized int getCount() {
        return count;
    }
}
