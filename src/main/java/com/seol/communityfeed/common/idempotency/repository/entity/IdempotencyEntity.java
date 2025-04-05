package com.seol.communityfeed.common.idempotency.repository.entity;

import com.seol.communityfeed.common.idempotency.Idempotency;
import com.seol.communityfeed.common.utils.ResponseObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "coummunity_idempotency")
@NoArgsConstructor
@AllArgsConstructor
public class IdempotencyEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    @Getter
    @Column(nullable = false)
    private String response;

    public IdempotencyEntity(Idempotency idempotency){
        this.idempotencyKey = idempotency.getKey();
        this.response = ResponseObjectMapper.toStringResponse(idempotency.getResponse());
    }

    public Idempotency toIdempotency(){
        return new Idempotency(this.idempotencyKey, ResponseObjectMapper.toResponseObject(response));
    }
}
