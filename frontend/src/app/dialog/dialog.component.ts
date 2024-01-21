import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserService } from '../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css'],
})
export class DialogComponent implements OnInit {
  PriorityLevel = ['Urgent', 'High', 'Normal', 'Low'];
  taskForm!: FormGroup;
  ActionBtn: string = 'Add';
  taskId: string = '';

  minDate: Date = new Date();

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public editData: any,
    private dialogRef: MatDialogRef<DialogComponent>,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.taskForm = this.formBuilder.group({
      taskId: ['', Validators.required],
      taskName: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required],
      dueDate: ['', Validators.required],
      priority: ['', Validators.required],
      status: ['TO DO', Validators.required],
      comments: ['', Validators.required],
    });

    if (this.editData) {
      this.ActionBtn = 'Update';
      this.taskId = this.editData.taskId; // Assuming the ID is in TaskId property
      console.log('ID to update:', this.taskId);

      this.taskForm.patchValue({
        taskId: this.editData.taskId,
        taskName: this.editData.taskName,
        category: this.editData.category,
        description: this.editData.description,
        dueDate: this.editData.dueDate,
        priority: this.editData.priority,
        status: this.editData.status,
        comments: this.editData.comments,
      });
    }
  }

  addTask() {
    if (!this.editData) {
      if (this.taskForm.valid) {
        console.log('Adding task with data:', this.taskForm.value);

        this.userService.postTask(this.taskForm.value).subscribe({
          next: (res) => {
            console.log('Add task response:', res);
            this.snackBar.open('Task Added', 'success', {
              horizontalPosition: 'center',
              verticalPosition: 'top',
              duration: 5000,
            });
            this.taskForm.reset();
            this.dialogRef.close('Save');
            location.reload();
          },
          error: (err) => {
            console.error('Error while adding the task:', err);
            alert('Error while adding the task');
          },
        });
      }
    } else {
      this.updateTask();
    }
  }

  // deleteTask() {
  //   if (this.taskId) {
  //     this.userService.deleteTask(this.taskId).subscribe({
  //       next: () => {
  //         console.log('Task deleted successfully');
  //         this.snackBar.open('Task Deleted', 'success', {
  //           horizontalPosition: 'center',
  //           verticalPosition: 'top',
  //           duration: 5000,
  //         });
  //         this.dialogRef.close('deleted');
  //         location.reload();
  //       },
  //       error: (err) => {
  //         console.error('Error deleting the task:', err);
  //         alert('Error deleting the task');
  //       },
  //     });
  //   }
  // }

  updateTask() {
    console.log('Updating task with data:', this.taskForm.value);
    console.log('TaskId to update:', this.taskId);

    this.userService.putTask(this.taskForm.value).subscribe({
      next: (res) => {
        console.log('Update response:', res);
        this.snackBar.open('Task Updated', 'success', {
          horizontalPosition: 'center',
          verticalPosition: 'top',
          duration: 5000,
        });
        location.reload();
        this.taskForm.reset();
        this.dialogRef.close('updated');
      },
      error: (err) => {
        console.error('Error while updating the record:', err);
        alert("Task ID cann't be updated !");
      },
    });
  }
}
