import { Component, Input, OnInit, Type ,  ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { EquipmentServices } from '../../../service/equipment.service';


@Component({
  selector: 'app-search-equipment',
  standalone: false,
  templateUrl: './search-equipment.component.html',
  styleUrl: './search-equipment.component.css'
})


export class SearchEquipmentComponent implements OnInit  {

  equipmentList: any = [];
  constructor(  private equipmentServices: EquipmentServices, private router: Router) {}

  ngOnInit(): void {
    console.log( "init " )
    this.getAllEquipment();
    //this.equipmentServices.getAllEquipment().subscribe(data => this.equipmentList = data);
  }



  viewEquipmentDetails(id: number) {
    console.log('Equipment Details:', id);
      //this.router.navigate(['/equipment', id]);
  }

  async getAllEquipment() {
    console.log( "get all equipment" )
    this.equipmentServices.getAllEquipment().subscribe((data : any) => {
      if (data != null && data.body != null) {
        var resultData = data.body;
        if (resultData) {
          this.equipmentList = resultData;
        }
      }
    },
    (error : any)=> {
        if (error) {
          if (error.status == 404) {
            if(error.error && error.error.message){
              this.equipmentList = [];
            }
          }
        }
      });
  }
}
