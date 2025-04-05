package com.seol.communityfeed.admin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seol.communityfeed.admin.ui.dto.GetTableListResponse;
import com.seol.communityfeed.admin.ui.dto.users.GetUserTableRequestDto;
import com.seol.communityfeed.admin.ui.dto.users.GetUserTableResponseDto;
import com.seol.communityfeed.admin.ui.query.AdminTableQueryRepository;
import com.seol.communityfeed.auth.repository.entity.QUserAuthEntity;
import com.seol.communityfeed.user.repository.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminTableQueryRepositoryImpl implements AdminTableQueryRepository {
    private final JPAQueryFactory queryFactory;
    private static final QUserAuthEntity userAuthEntity = QUserAuthEntity.userAuthEntity;
    private static final QUserEntity userEntity = QUserEntity.userEntity;

    @Override
    public GetTableListResponse<GetUserTableResponseDto> getUserTableData(GetUserTableRequestDto dto) {

        int total = queryFactory.select(userEntity.id)
                .from(userEntity)
                .where(likeName(dto.getName()))
                .fetch()
                .size();
        List<GetUserTableResponseDto> result = queryFactory
                .select(
                        Projections.fields(
                                GetUserTableResponseDto.class,
                                userEntity.id.as("id"),
                                userAuthEntity.email.as("email"),
                                userEntity.info.name.as("name"),
                                userAuthEntity.role.as("role"),
                                userEntity.regDt.as("createdAt"),
                                userEntity.updDt.as("updatedAt"),
                                userAuthEntity.lastLoginDt.as("lastLoginAt")
                        )
                )
                .from(userEntity)
                .join(userAuthEntity).on(userAuthEntity.userId.eq(userEntity.id))
                .where(likeName(dto.getName()))
                .orderBy(userEntity.id.desc())
                .offset(dto.getOffset())
                .limit(dto.getLimit())
                .fetch();
                return new GetTableListResponse<>(total, result);
    }

    private BooleanExpression likeName(String name){
        if(name == null || name.isBlank()){
            return null;
        }
        return userEntity.info.name.like("%" + name + "%");
    }
}
