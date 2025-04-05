package com.seol.communityfeed.common.idempotency.repository;

import com.seol.communityfeed.common.idempotency.Idempotency;
import com.seol.communityfeed.common.idempotency.IdempotencyRepository;
import com.seol.communityfeed.common.idempotency.repository.entity.IdempotencyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IdempotencyRepositoryImpl implements IdempotencyRepository {
    private final JpaIdempotencyRepository jpaIdempotencyRepository;

    @Override
    public Idempotency getByKey(String key) {
        Optional<IdempotencyEntity> idempotencyEntity = jpaIdempotencyRepository.findByIdempotencyKey(key);
        return idempotencyEntity.map(IdempotencyEntity::toIdempotency).orElse(null);
    }

    @Override
    public void save(Idempotency idempotency) {
        jpaIdempotencyRepository.save(new IdempotencyEntity(idempotency));
    }
}
