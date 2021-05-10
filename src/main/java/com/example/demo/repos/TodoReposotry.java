package com.example.demo.repos;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Todo;

public interface TodoReposotry extends CrudRepository<Todo, Integer>{

}
