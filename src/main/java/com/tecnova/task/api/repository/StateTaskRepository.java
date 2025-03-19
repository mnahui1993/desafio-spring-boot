package com.tecnova.task.api.repository;

import com.tecnova.task.api.model.StateTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateTaskRepository extends CrudRepository<StateTask,Integer> {
}
