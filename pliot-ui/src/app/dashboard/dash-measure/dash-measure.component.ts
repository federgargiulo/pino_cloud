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

    this.route.paramMap.subscribe( params => {
      this.dashId = params.get('id') || ''; // Assicura che non sia null
      console.log(" Dashboard ID ricevuto:", this.dashId );
       if ( this.dashId !){
        this.userDashboardService.getUserDashboardById( this.dashId ).subscribe(
           {
              next: (data) => {
              this.loadChartsInfo( data.body );
            },
            error: (err) => {
              console.error('Errore nel caricamento del dettaglio della dashboard', err);

            }
          }
        )

      }

      })



   }

 formatDateTime(input: string): string {
  const date = new Date(input);
  const pad = (n: number) => n.toString().padStart(2, '0');

  const day = pad(date.getDate());
  const month = pad(date.getMonth() + 1);
  const year = date.getFullYear();
  const hour = pad(date.getHours());
  const minute = pad(date.getMinutes());

  return `${day}/${month}/${year} ${hour}:${minute}`;
}

   getNewConfiguration( tp: string , ll : string ): any  {

    return {
      type: tp ,
      data: {
        labels: [] as string[],  // Inizializziamo l'array dei label
        datasets: [
          {
            label: ll,
            data: [] as number[],
            backgroundColor: 'blue'
          }
        ]
      }
    }
  }

  loadChartsInfo( data:any  ){
    
     let d: any;

      try {
        d = typeof data.configuration === 'string'
          ? JSON.parse(data.configuration)
          : data.configuration;
      } catch (e) {
        console.error('❌ Errore parsing JSON configuration:', data.configuration);
        return;
      }

      if (!Array.isArray(d.signals)) {
        console.warn('⚠️ Nessun campo signals valido in configurazione:', d);
        return;
      }
 
    d.signals.forEach( ( x :any , index : any)  => {
      var newconf = this.getNewConfiguration(x.chartType ,  x.label );

      this.measureService.findMeasures( x.signalId ).subscribe(  {
          next: (data) => {

              data.body.results.forEach( ( x: any ) => {
                newconf.data.labels.push( this.formatDateTime( x.measureDttm ) );
                newconf.data.datasets[0].data.push( x.val );
              } );
              this.setDataAndChart( index , newconf );

          },
          error: (err) => {
            console.error('Errore nel caricamento del dettaglio della dashboard', err);
          }
      } );

    } );
  }


  chartData: any[] = []; // Array che conterrà i dati dei grafici
  charts: Chart[] = []; //

  setDataAndChart( i : any , confdati:any ){

    this.chartData[i] = confdati;

    setTimeout(() => this.renderChart(i), 100);
  }
  renderChart(index: number) {
    const canvasId = `chart-${index}`;
    const canvas = document.getElementById(canvasId) as HTMLCanvasElement;

    if (!canvas) {
      console.error(`Canvas con id ${canvasId} non trovato.`);
      return;
    }

    if (this.charts[index]) {
      this.charts[index].destroy(); // Elimina il vecchio grafico se esiste già
    }

    this.charts[index] = new Chart(canvas, this.chartData[index]);
  }

  loadChartInfo( data:any ):void{
     this.measureService.findMeasures( "test" ).subscribe(
        {
          next: (data) => {
             data.body.results.forEach( ( x: any ) => {

             this.dashConfiguration.data.labels.push( x.measureDttm );
             this.dashConfiguration.data.datasets[0].data.push( x.val );


            });
            this.dashConfiguration.type='';
            this.chart =  new Chart( 'Monitor' , this.dashConfiguration );


          },
          error: (err) => {
            console.error('Errore nel caricamento del dettaglio della dashboard', err);

          }
        }
      )

    }





}
