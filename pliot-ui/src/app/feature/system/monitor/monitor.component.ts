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

  @ViewChild('barCanvas') barCanvas!: ElementRef<HTMLCanvasElement>;
  chart!: Chart;
  
  constructor(private dbmsService: CommonService) {}


  ngOnInit() {
    this.dbmsService.getDbmsSize().subscribe((data) => {
     
      const records = data.body.DBMS;
       alert( records )
      const grouped = new Map<string, number>();

      for (const entry of records) {
        const date = new Date(entry.report_dttm).toLocaleString();
        const val = parseInt(entry.val, 10);
        grouped.set(date, (grouped.get(date) || 0) + val);
      }

      const labels = Array.from(grouped.keys());
      const values = Array.from(grouped.values());

      this.renderChart(labels, values);
    });
  }

  renderChart(labels: string[], data: number[]) {
    if (this.chart) {
      this.chart.destroy(); // Previene duplicati se ricarichi
    }

    const ctx = this.barCanvas.nativeElement.getContext('2d');
    if (!ctx) return;

    this.chart = new Chart(ctx, {
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
}