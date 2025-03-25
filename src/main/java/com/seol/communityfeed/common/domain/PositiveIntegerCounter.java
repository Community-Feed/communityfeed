package com.seol.communityfeed.common.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
public class PositiveIntegerCounter {

    private int count;

    @Transient // 👉 DB 매핑 제외
    private int maxLimit = 1000;

    public PositiveIntegerCounter() {
        this.count = 0;
        this.maxLimit = 1000; // ✅ 기본값 명시
    }

    public PositiveIntegerCounter(int maxLimit) {
        if (maxLimit <= 0) {
            throw new IllegalArgumentException("최대 카운트는 0보다 커야 합니다.");
        }
        this.count = 0;
        this.maxLimit = maxLimit;
    }

    public PositiveIntegerCounter(int count, int maxLimit) {
        if (maxLimit <= 0) {
            throw new IllegalArgumentException("최대 카운트는 0보다 커야 합니다.");
        }
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
