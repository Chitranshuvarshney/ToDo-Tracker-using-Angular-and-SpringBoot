import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent {
  filterByStatus: string = 'ALL';

  @Output()
  filteredStatus: EventEmitter<string> = new EventEmitter<string>();

  Onfilter() {
    // if (!this.filterByStatus) {
    //   this.filterByStatus = 'ALL';
    // } else {
    this.filteredStatus.emit(this.filterByStatus);
    // }
  }
}
