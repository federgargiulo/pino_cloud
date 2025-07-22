import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CommonService } from '../../../service/common.service';
import { Chart, ChartConfiguration, ChartOptions, ChartType, ChartTypeRegistry } from 'chart.js';


@Component({
  selector: 'app-monitor',
  standalone: false,
  templateUrl: './monitor.component.html',
  styleUrl: './monitor.component.css'
})
export class MonitorComponent implements OnInit {

  @ViewChild('memoryChart') memoryChartCamvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('connectionChart') connectionChartCamvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('podChart') podChartCamvas!: ElementRef<HTMLCanvasElement>;
  memoryChart!: Chart;
  connectionChart!:Chart;
  
  constructor(private dbmsService: CommonService) {}


  ngOnInit() {
    this.dbmsService.getDbmsSize().subscribe((data) => {
     
      this.renderMemoryChart( data.body.DBMS );
      this.renderConnectionChart( data.body.CONN_STATUS );
      this.renderPodChart( data.body.POD_MEM );
    });
  }
   

  renderMemoryChart( records:any  ) {

     const grouped = new Map<string, number>();
      for (const entry of records) {
        const date = new Date(entry.report_dttm).toLocaleString();
        const val = parseInt(entry.val, 10);
        grouped.set(date, (grouped.get(date) || 0) + val);
      }
      const labels = Array.from(grouped.keys());
      const data = Array.from(grouped.values());
    
    if (this.memoryChart) {
      this.memoryChart.destroy(); // Previene duplicati se ricarichi
    }

    const ctx = this.memoryChartCamvas.nativeElement.getContext('2d');
    if (!ctx) return;

    this.memoryChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Valori (KB)',
            data,
            backgroundColor: '#42A5F5',
            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }




  renderConnectionChart( records : any) {
 
     const grouped = new Map<string, number>();
     for (const entry of records) {
        const lab =  entry.status ;
        const val = parseInt(entry.total, 10);
        grouped.set(lab, (grouped.get(lab) || 0) + val);
      }
      const labels = Array.from(grouped.keys());
      const data = Array.from(grouped.values());
      const ctx =this.connectionChartCamvas.nativeElement.getContext('2d');
      if (ctx) {
        new Chart(ctx, {
          type: 'pie',
          data: {
            labels,
            datasets: [{
              label: 'Stati connessioni',
              data,
              backgroundColor: [
                '#42A5F5', '#66BB6A', '#FFA726', '#EF5350', '#AB47BC'
              ]
            }]
          },
          options: {
            responsive: true,
            plugins: {
              legend: {
                position: 'right'
              }
            }
          }
        });
      }
    
  }

  renderPodChart( podMem : any ) {
    const max = +podMem.find( (m: { status: string; })  => m.status === 'Max')?.total || 0;
    const used = +podMem.find((m: { status: string; }) => m.status === 'Tot')?.total || 0;
    const free = +podMem.find((m: { status: string; }) => m.status === 'Free')?.total || 0;
    const other = Math.max(0, max - used - free); // Protezione da valori negativi
  
    const labels = ['Used', 'Free', 'Other'];
    const data = [
      Math.round(used / 1024 / 1024),
      Math.round(free / 1024 / 1024),
      Math.round(other / 1024 / 1024),
    ];
       const ctx =this.podChartCamvas.nativeElement.getContext('2d');

        if (!ctx) return;

  new Chart(ctx, {
    type: 'pie',
    data: {
      labels,
      datasets: [{
        label: 'POD Memory (MB)',
        data,
        backgroundColor: ['#FF7043', '#66BB6A', '#90CAF9']
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'bottom'
        },
        tooltip: {
          callbacks: {
            label: (context) => {
              const value = context.raw as number;
              const total = data.reduce((a, b) => a + b, 0);
              const percent = ((value / total) * 100).toFixed(1);
              return `${context.label}: ${value} MB (${percent}%)`;
            }
          }
        }
      }
    }
  });
  }

}