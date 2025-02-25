import { Component, Input, OnInit, Type ,  ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EquipmentServices } from '../../../service/equipment.service';
@Component({
  selector: 'app-add-equipment',
  standalone: false,
  templateUrl: './add-equipment.component.html',
  styleUrl: './add-equipment.component.css'
})
export class AddEquipmentComponent implements OnInit {

  addEquipmentForm: EquipmentForm = new EquipmentForm();
  @ViewChild("equipmentForm")
  EquipmentForm!: NgForm;



  isSubmitted: boolean = false;
  constructor(private router: Router, private httpProvider: EquipmentServices ) { }
  ngOnInit(): void {  }

  addEquipment(isValid: any) {
    this.isSubmitted = true;
    if (isValid) {
      console.info( " addEquipmentForm " + this.addEquipmentForm.name )
      this.httpProvider.saveEquipment({name:  this.addEquipmentForm.name }).subscribe(async data => {
        if (data != null && data.body != null) {
          if (data != null && data.body != null) {
            var resultData = data.body;
            if (resultData != null && resultData.isSuccess) {

              setTimeout(() => {
                this.router.navigate(['/']);
              }, 500);
            }
          }
        }
      },
        async error => {

          setTimeout(() => {
            this.router.navigate(['/']);
          }, 500);
        });
    }
  }
}




export class EquipmentForm {
  equipmentId: string="";
  tenant: string="";
  name: string="";
  version: string="";
}
