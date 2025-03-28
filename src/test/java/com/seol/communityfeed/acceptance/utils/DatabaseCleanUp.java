package com.seol.communityfeed.acceptance.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("test")
@Component
public class DatabaseCleanUp implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(DatabaseCleanUp.class); // 직접 선언

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;
    private List<String> notGeneratedIdTableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities()
                .stream()
                .filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
                .map(entity -> entity.getJavaType().getAnnotation(Table.class).name())
                .toList();

        notGeneratedIdTableNames = List.of("community_user_auth", "community_user_relation", "community_like");
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();

            if (!notGeneratedIdTableNames.contains(tableName)) {
                try {
                    entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1")
                            .executeUpdate();
                } catch (Exception e) {
                    log.warn("Could not reset ID for table: {}", tableName);
                }
            }
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}