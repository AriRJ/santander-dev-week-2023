package me.dio.service;

import me.dio.domain.model.Task;
import me.dio.domain.repository.TaskRepository;
import me.dio.dto.TaskDTO;
import me.dio.exception.TaskNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return convertToDTO(task);
    }

    public TaskDTO save(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        task.setCreatedAt(LocalDateTime.now());
        return convertToDTO(taskRepository.save(task));
    }

    public TaskDTO update(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        BeanUtils.copyProperties(taskDTO, existingTask, "id", "createdAt");
        existingTask.setCompletedAt(taskDTO.getCompletedAt());
        return convertToDTO(taskRepository.save(existingTask));
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        BeanUtils.copyProperties(task, taskDTO);
        return taskDTO;
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);
        return task;
    }
}
