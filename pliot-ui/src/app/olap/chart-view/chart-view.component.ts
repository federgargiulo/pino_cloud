import { Component, Input, OnChanges, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { Chart, ChartConfiguration, ChartType } from 'chart.js';

@Component({
  selector: 'app-chart-view',
  templateUrl: './chart-view.component.html',
  styleUrls: ['./chart-view.component.css'],
})
export class ChartViewComponent implements OnChanges, AfterViewInit {
  @Input() data: any[] = []; // Dati in input
  @ViewChild('chartDiv') chartCanvas!: ElementRef; // Riferimento al canvas per Chart.js

  public lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      { data: [], label: 'Min', fill: false, borderColor: 'blue' },
      { data: [], label: 'Mean', fill: false, borderColor: 'green' },
      { data: [], label: 'Max', fill: false, borderColor: 'red' }
    ]
  };

  public lineChartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    scales: {
      x: {
        title: {
          display: true,
          text: 'Time Period'
        }
      },
      y: {
        title: {
          display: true,
          text: 'Values'
        },
        beginAtZero: true
      }
    }
  };

  public lineChartType: ChartType = 'line'; // Tipo di grafico

  private chartInstance!: Chart; // Istanza del grafico Chart.js

  ngOnChanges(): void {
    this.updateChart(); // Aggiorna il grafico quando i dati cambiano
  }

  ngAfterViewInit(): void {
    alert( " createChart "   );
    this.createChart(); // Crea il grafico dopo che la vista è stata inizializzata
  }

  // Metodo per aggiornare i dati nel grafico
  private updateChart(): void {
    alert( " update chart " + this.data );
    const labels = this.data.map(row => row.label);
    const min = this.data.map(row => row.min);
    const mean = this.data.map(row => row.mean);
    const max = this.data.map(row => row.max);

    this.lineChartData.labels = labels;
    this.lineChartData.datasets[0].data = min;
    this.lineChartData.datasets[1].data = mean;
    this.lineChartData.datasets[2].data = max;

    // Aggiorna il grafico se già esiste
    if (this.chartInstance) {
      alert( " update chartInstance "   );
   
      this.chartInstance.update();
    }
  }

  // Metodo per creare il grafico inizialmente
  private createChart(): void {
    if (this.chartCanvas) {
      const ctx = this.chartCanvas.nativeElement.getContext('2d'); // Ottieni il contesto 2d del canvas
      alert( " contesto " + ctx );
      if (ctx) {
        this.chartInstance = new Chart(ctx, {
          type: "line",
          data: this.lineChartData,
          options: this.lineChartOptions
        });
      }
    }
  }
}
