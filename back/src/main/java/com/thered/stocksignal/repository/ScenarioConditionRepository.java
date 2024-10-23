package com.thered.stocksignal.repository;

import com.thered.stocksignal.domain.entity.ScenarioCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioConditionRepository extends JpaRepository<ScenarioCondition, Long> {
}
