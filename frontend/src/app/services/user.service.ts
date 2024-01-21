import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  url = 'http://localhost:9000/api/v3';

  constructor(private http: HttpClient) {}

  postTask(data: any) {
    return this.http.post(`${this.url}/user/task`, data);
  }

  getTask() {
    return this.http.get(`${this.url}/user/tasks`);
  }

  putTask(data: any) {
    return this.http.put(`${this.url}/user/task`, data);
  }

  deleteTask(taskId: string) {
    return this.http.delete(`${this.url}/user/task/${taskId}`);
  }

  postMail(data: any) {
    return this.http.post(`https://formspree.io/f/mjvnvldp`, data);
  }
}
