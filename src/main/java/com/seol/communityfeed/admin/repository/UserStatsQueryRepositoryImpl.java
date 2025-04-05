package com.seol.communityfeed.admin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seol.communityfeed.admin.ui.dto.users.GetDailyRegisterUserResponseDto;
import com.seol.communityfeed.admin.ui.query.UserStatsQueryRepository;
import com.seol.communityfeed.common.utils.TimeCalculator;
import com.seol.communityfeed.user.repository.entity.QUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserStatsQueryRepositoryImpl implements UserStatsQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QUserEntity userEntity = QUserEntity.userEntity;

    @Override
    public List<GetDailyRegisterUserResponseDto> getDailyRegisterUserStats(int beforeDays){
        return queryFactory
                .select(
                        Projections.fields(
                                GetDailyRegisterUserResponseDto.class,
                                userEntity.regdate.as("date"),
                                userEntity.count().as("count")
                        )
                ).from(userEntity)
                .where(userEntity.regdate.after(TimeCalculator.getDateDaysAgo(beforeDays)))
                .groupBy(userEntity.regdate)
                .orderBy(userEntity.regdate.asc())
                .fetch();
    }
}
