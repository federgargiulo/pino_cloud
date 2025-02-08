import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-help-agular',
  standalone: false,
  templateUrl: './help-angular.component.html',
  styleUrl: './help-angular.component.css'
})
export class HelpAngularComponent {
  constructor(private modalService: NgbModal) {}
}
