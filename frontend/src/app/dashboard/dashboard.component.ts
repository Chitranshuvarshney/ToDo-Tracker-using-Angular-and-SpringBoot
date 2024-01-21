import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { UserService } from '../services/user.service';
import { DialogComponent } from '../dialog/dialog.component';
import { Task } from '../model/task';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TaskService } from '../services/task.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  tasks: Task[] = [];
  filteredTasks: any[] = [];
  statusFilter: string = 'ALL';

  displayedColumns: string[] = [
    'taskId',
    'taskName',
    'description',
    'category',
    'dueDate',
    'priority',
    'status',
    'comments',
    'Action',
  ];

  dataSource!: MatTableDataSource<Task>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  submitStatus: boolean = false;

  canDeactivate() {
    if (!this.submitStatus)
      this.submitStatus = confirm(
        'You have not added any task. Any task details entered will be lost. Are you sure you want to leave?'
      );
    return this.submitStatus;
  }

  constructor(
    private dialog: MatDialog,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private taskService: TaskService
  ) {}

  ngOnInit(): void {
    this.getAllTasks();
    this.taskService.getTasks().subscribe((tasks) => {
      this.tasks = tasks;
    });
  }

  openDialog() {
    this.dialog
      .open(DialogComponent, {
        width: '30%',
      })
      .afterClosed()
      .subscribe((val) => {
        if (val === 'save') {
          this.getAllTasks();
        }
      });
  }

  getAllTasks() {
    this.userService.getTask().subscribe({
      next: (res: any) => {
        console.log('API Response:', res);
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.tasks = res;
        this.onFilter('ALL');
      },
      error: (err) => {
        console.error('API Error:', err);
        alert('Error while fetching the Records!!');
      },
    });
  }

  editTask(row: any) {
    this.dialog
      .open(DialogComponent, {
        width: '30%',
        data: row,
      })
      .afterClosed()
      .subscribe((val) => {
        if (val === 'update') {
          this.getAllTasks();
        }
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

  onFilter(filter: string) {
    this.statusFilter = filter;

    if (this.statusFilter === 'ALL') {
      this.filteredTasks = this.tasks;
    } else {
      this.filteredTasks = this.tasks.filter(
        (task) => task.status === this.statusFilter
      );
    }
  }

  archiveTask(task: Task) {
    this.taskService.archiveTask(task);
    this.snackBar.open('Task Archived', 'success', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 5000,
    });
    location.reload();
  }

  deleteArchiveTask(taskId: string): void {
    const confirmed = window.confirm(
      'Are you sure you want to archive this task?'
    );

    if (confirmed) {
      this.userService.deleteTask(taskId).subscribe({
        next: (res) => {
          location.reload();
        },
        error: (err) => {
          console.error('Error archiving the task:', err);
          alert('Error while archiving the task!!');
        },
      });
    }
  }

  onClick(row: any) {
    this.archiveTask(row);
    this.deleteArchiveTask(row.taskId);
  }
}
