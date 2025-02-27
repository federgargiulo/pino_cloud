import { Component , OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {Chart , registerables } from 'chart.js'
import { UserDashboardService } from '../../service/user-dashboard.service';
import { MeasureService } from '../../service/measure.service';
 

Chart.register( ... registerables )
@Component({
  selector: 'app-dash-measure',
  standalone: false,
  templateUrl: './dash-measure.component.html',
  styleUrl: './dash-measure.component.css'
})
export class DashMeasureComponent implements OnInit {

  constructor( private route: ActivatedRoute , 
     private userDashboardService: UserDashboardService ,
     private measureService : MeasureService ) {
      
      }  
  

  

  public config: any = {
    type: 'bar',
    data: {
      labels: ['12:00', '12:05', '12:10' , '12:15' ],
      datasets: [
        { 
        label: 'Temp [C]', 
        data: [ '20' , '23' , '21' , '23'],
        backgroundColor: 'blue'
       },
       { 
        label: 'Press [pascal]', 
        data: [ '200' , '209' , '190' , '200'],
        backgroundColor: 'red'
      }
      ]
    }
  ,
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    },
  };
  chart: any;

  dashId ='';

  public dashConfiguration: any = {
    type: 'bar',
    data: {
      labels: [] as string[],  // Inizializziamo l'array dei label
      datasets: [
        { 
          label: 'Temp [C]', 
          data: [] as number[],
          backgroundColor: 'blue'
        }
      ]
    }
  };


  ngOnInit(): void { 
    
    this.route.paramMap.subscribe(params => {
      this.dashId = params.get('id') || ''; // Assicura che non sia null
      console.log("Equipment ID ricevuto:", this.dashId );
      alert( " dashid " + this.dashId );
      this.userDashboardService.getUserDashboardById( this.dashId ).subscribe(
          {
            
            next: (data) => {
             
              this.loadChartInfo( data.body );
            },
            error: (err) => {
              console.error('Errore nel caricamento del dettaglio della dashboard', err);
              
            }
          }
        )

      })


    
   }

   loadChartInfo( data:any ):void{
      this.measureService.findMeasures( "test" ).subscribe(
        {
          next: (data) => {
             data.body.results.forEach( ( x: any ) => {
              
             this.dashConfiguration.data.labels.push( x.mesure_dttm );
             this.dashConfiguration.data.datasets[0].data.push( x.val );
             
              
            });
            this.chart =  new Chart( 'Monitor' , this.dashConfiguration );
            
            
          },
          error: (err) => {
            console.error('Errore nel caricamento del dettaglio della dashboard', err);
            
          }
        }
      )

    }

      

   

}
