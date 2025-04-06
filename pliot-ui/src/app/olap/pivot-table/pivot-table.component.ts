import { Component, OnInit } from '@angular/core';
import { OlapService } from '../olap.service';
import { TenantServices } from '../../service/tenant.service';
import { EquipmentServices } from '../../service/equipment.service';
import { Observable } from 'rxjs';
import { SignalServices } from '../../service/signal.service';

@Component({
  selector: 'app-pivot-table',
  standalone: false,
  templateUrl: './pivot-table.component.html',
  styleUrl: './pivot-table.component.css'
})
export class PivotTableComponent implements OnInit {
  data: any[] = [];
 
  dateRange: { start: Date | null, end: Date | null } = { start: null, end: null };
  minDate: Date;
  maxDate: Date;
  selectedAggregation: string = 'MONTH';

  tenantList: any = [];
  selectedTenantId: string | null = null;
  selectedTenantName: string | null = null;


  equipmentList: any = [];
  
  selectedEquipmentId: string | null = null;
  selectedEquipmentName: string | null = null;

  
  signalList: any = [];
  
  selectedSignalId: string | null = null;
  selectedSignalName: string | null = null;
  
  constructor(private olapService: OlapService,
              private tenantServices: TenantServices, 
              private equipmentService: EquipmentServices,
              private signalService: SignalServices
  ) {
    this.minDate = new Date();
    this.minDate.setFullYear(this.minDate.getFullYear() - 1);

    // Imposta la data massima (oggi)
    this.maxDate = new Date();
  }

  ngOnInit() {
    this.loadData();
    this.getAllTenants();

  }

  async getAllTenants(): Promise<void> {
    
    await this.fetchData(() => this.tenantServices.getAllTenants(), 'tenantList');
  }


  async onTenantChange( tenantId: string ){
    this.selectedTenantId = tenantId;
    const selected = this.tenantList.find( ( x:any )  => x.tenantId === tenantId);
    this.selectedTenantName = selected?.name || null;
    if ( "select-id"!= tenantId ){
      await this.fetchData(() => this.equipmentService.getEquipmentsByTenant( tenantId ), 'equipmentList');
    
    }
      
  }

  async onEquipmentChange( equipmentId: string ){
    this.selectedEquipmentId = equipmentId;
    const selected = this.equipmentList.find( ( x : any ) => x.equipmentId === equipmentId);
    this.selectedEquipmentName = selected?.name || null;
  
    if ( "select-id"!= equipmentId )
      await this.fetchData(() => this.signalService.getSignalsByEquipmentId( equipmentId), 'signalList');
  }

  


  async loadData(parent?: string) {
         
    if ( this.selectedSignalId != null ){
      var sigalId = this.selectedSignalId;
      await this.fetchData(() =>  this.olapService.getAggregation( sigalId ,
         this.dateRange , this.selectedAggregation ) , 'data');   
    }
  }

  onSignalChange(  signalId: string ){
    this.selectedSignalId = signalId;
    const selected = this.signalList.find( ( x:any) => x.signalId === signalId);
    this.selectedSignalName = selected?.name || null;
  }
  

  reload(row: any) {
     
    this.loadData(row.id);
  }

  async fetchData(serviceCall: () => Observable<any>, targetProperty: string): Promise<void> {
    console.log("Fetching data...");
    
    serviceCall().subscribe((data: any) => {
      console.log("Dati ricevuti dal server:", data);
      if (data != null && data.body != null) {
        const resultData = data.body;
  
        if (resultData) {
          (this as any)[targetProperty] = resultData;
        }
      }
    }, (error: any) => {
      if (error) {
        if (error.status === 404) {
          if (error.error && error.error.message) {
            (this as any)[targetProperty] = [];
          }
        }
      }
    });
  }
}
