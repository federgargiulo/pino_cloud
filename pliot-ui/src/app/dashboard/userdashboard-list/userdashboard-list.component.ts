import { Component, Injectable, OnInit } from '@angular/core';
import { Router } from 'express';
import { UserDashboardService } from '../../service/user-dashboard.service';

@Component({
  selector: 'app-userdashboard-list',
  standalone: false,
  templateUrl: './userdashboard-list.component.html',
  styleUrl: './userdashboard-list.component.css'
})

export class UserdashboardListComponent implements OnInit {
 
  dashBoardsList: any []= [];

  isLoading: boolean = true;
   

 constructor( private userDashboardService: UserDashboardService ){
    console.info( " sono nel costruttore ");
 } 

  ngOnInit(): void {
    console.info( "load list ")

    this.userDashboardService.getUserDashboards().subscribe(
      {
        next: (data) => {
          this.dashBoardsList = data.body;
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Errore nel caricamento dei dati', err);
          this.isLoading = false;
        }
      });
   }


}
