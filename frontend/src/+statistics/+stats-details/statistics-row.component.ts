import { Component, Input } from "@angular/core";

import { Statistics } from "../statistics.model";

@Component({
  selector: '[app-statistitcs-row]',
  templateUrl: './statistics-row.component.html'
})

export class StatisticsRowComponent {
  @Input() statistics: Statistics;
}
