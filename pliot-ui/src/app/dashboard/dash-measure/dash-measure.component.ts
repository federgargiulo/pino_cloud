import { Component , OnInit} from '@angular/core';
import {Chart , registerables } from 'chart.js'
 

Chart.register( ... registerables )
@Component({
  selector: 'app-dash-measure',
  standalone: false,
  templateUrl: './dash-measure.component.html',
  styleUrl: './dash-measure.component.css'
})
export class DashMeasureComponent implements OnInit {

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

  ngOnInit(): void { 
    this.chart = new Chart( 'Monitor' , this.config );
   }
}
