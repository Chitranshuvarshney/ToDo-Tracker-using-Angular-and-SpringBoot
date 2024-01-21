import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { UserService } from '../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TaskService } from '../services/task.service';
import { Task } from '../model/task';

@Component({
  selector: 'app-archive',
  templateUrl: './archive.component.html',
  styleUrls: ['./archive.component.css'],
})
export class ArchiveComponent implements OnInit {
  tasks: Task[] = [];
  archivedTasks: Task[] = [];

  displayedColumns: string[] = [
    'taskId',
    'taskName',
    'description',
    'category',
    'dueDate',
    'priority',
    'status',
    'comments',
    // 'Action',
  ];

  dataSource!: MatTableDataSource<Task>;

  constructor(
    private userService: UserService,
    private snackBar: MatSnackBar,
    private taskService: TaskService
  ) {}

  ngOnInit(): void {
    this.taskService.getArchivedTasks().subscribe((archivedTasks) => {
      this.archivedTasks = archivedTasks;
    });
  }

  deleteTask(taskId: string): void {
    const confirmed = window.confirm(
      'Are you sure you want to delete this task?'
    );

    if (confirmed) {
      this.userService.deleteTask(taskId).subscribe({
        next: (res) => {
          this.snackBar.open('Task Deleted', 'success', {
            horizontalPosition: 'center',
            verticalPosition: 'top',
            duration: 5000,
          });
          location.reload();
        },
        error: (err) => {
          console.error('Error deleting the task:', err);
          alert('Error while deleting the task!!');
        },
      });
    }
  }

  applySearch(event: Event) {
    const searchValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = searchValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  deleteAllArchive() {
    const confirmed = window.confirm(
      'Are you sure you want to delete all archive tasks ?'
    );
    if (confirmed) {
      localStorage.removeItem('archivedTasks');
      this.snackBar.open('All Task Deleted', 'success', {
        horizontalPosition: 'center',
        verticalPosition: 'top',
        duration: 5000,
      });
      location.reload();
    }
  }
}
