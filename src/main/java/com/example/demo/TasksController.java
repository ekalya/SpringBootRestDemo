/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author exk
 */
@RestController
@RequestMapping("/tasks")
public class TasksController {

    ArrayList<Task> tasks;

    public TasksController() {
        tasks = new ArrayList<>();
        tasks.add(new Task(1, "Task 1", "Description 1"));
        tasks.add(new Task(2, "Task 2", "Description 1"));
    }

    @ResponseBody
    @GetMapping("/")
    public Collection<Task> findAll() {
        return this.tasks;
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Collection<Task> findById(@PathVariable Integer id) {
        return this.tasks.stream().filter(t -> t.getId().equals(id)).collect(Collectors.toList());
    }

    @ResponseBody
    @PostMapping("/")
    public Task create(@RequestBody Task task) {
        task.setId(this.tasks.size() + 1);
        this.tasks.add(task);
        return task;
    }

    @ResponseBody
    @PutMapping("/")
    public Task update(@RequestBody Task task) {
        Optional<Task> ft = this.tasks.stream().filter(t -> t.getId().equals(task.getId())).findFirst();
        if (ft.isPresent()) {
            ft.get().setName(task.getName());
            ft.get().setDescription(task.getDescription());
        }
        return task;
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public Optional<Task> delete(@PathVariable Integer id) {
        Optional<Task> ft = this.tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
        if (ft.isPresent()) {
            this.tasks.remove(ft.get());
        }
        return ft;
    }
}
