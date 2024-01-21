import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, combineLatest, map } from 'rxjs';
import { Task } from '../model/task';

@Injectable({
  providedIn: 'root',
})
// export class TaskService {
//   constructor() {}

//   private tasks: BehaviorSubject<Task[]> = new BehaviorSubject<Task[]>([]);
//   private archivedTasks: BehaviorSubject<Task[]> = new BehaviorSubject<Task[]>(
//     []
//   );

//   getTasks() {
//     return this.tasks.asObservable();
//   }

//   getArchivedTasks() {
//     return this.archivedTasks.asObservable();
//   }

//   addTask(task: Task) {
//     this.tasks.next([...this.tasks.value, task]);
//   }

//   archiveTask(task: Task) {
//     this.archivedTasks.next([...this.archivedTasks.value, task]);
//     // Remove the task from the tasks array
//     this.tasks.next(
//       this.tasks.value.filter((task) => task.taskId !== task.taskId)
//     );
//   }
// }
export class TaskService {
  private tasks: BehaviorSubject<Task[]> = new BehaviorSubject<Task[]>([]);
  private archivedTasks: BehaviorSubject<Task[]> = new BehaviorSubject<Task[]>(
    []
  );

  constructor() {
    // Load tasks from local storage on service initialization
    this.loadTasks();
    this.loadArchivedTasks();
  }

  getTasks(): Observable<Task[]> {
    return combineLatest([this.tasks, this.archivedTasks]).pipe(
      map(([tasks, archivedTasks]) =>
        tasks.filter(
          (task) =>
            !archivedTasks.some(
              (archivedTask) => archivedTask.taskId === task.taskId
            )
        )
      )
    );
  }

  getArchivedTasks() {
    return this.archivedTasks.asObservable();
  }

  addTask(task: Task) {
    this.tasks.next([...this.tasks.value, task]);
    this.saveTasks();
  }

  archiveTask(task: Task) {
    this.archivedTasks.next([...this.archivedTasks.value, task]);
    this.tasks.next(
      this.tasks.value.filter((task) => task.taskId !== task.taskId)
    );
    this.saveTasks();
    this.saveArchivedTasks();
  }

  // Helper method to load tasks from local storage
  private loadTasks() {
    const storedTasks = localStorage.getItem('tasks');
    this.tasks.next(storedTasks ? JSON.parse(storedTasks) : []);
  }

  // Helper method to save tasks to local storage
  private saveTasks() {
    localStorage.setItem('tasks', JSON.stringify(this.tasks.value));
  }

  // Helper method to load archived tasks from local storage
  private loadArchivedTasks() {
    const storedArchivedTasks = localStorage.getItem('archivedTasks');
    this.archivedTasks.next(
      storedArchivedTasks ? JSON.parse(storedArchivedTasks) : []
    );
  }

  // Helper method to save archived tasks to local storage
  private saveArchivedTasks() {
    localStorage.setItem(
      'archivedTasks',
      JSON.stringify(this.archivedTasks.value)
    );
  }

  deleteArchivedTask(task: Task) {
    this.archivedTasks.next(
      this.archivedTasks.value.filter((task) => task.taskId !== task.taskId)
    );
    this.saveArchivedTasks();
  }
}
