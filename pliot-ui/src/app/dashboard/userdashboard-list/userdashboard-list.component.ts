import { Component, OnInit } from '@angular/core';
import { Router } from 'express';
import { UserDashboardService } from '../../service/user-dashboard.service';

@Component({
  selector: 'app-userdashboard-list',
  standalone: false,
  templateUrl: './userdashboard-list.component.html',
  styleUrl: './userdashboard-list.component.css'
})


export class UserdashboardListComponent implements OnInit {

  dashBoardsList: any = [];

 constructor( private userDashboardService: UserDashboardService ){} 

  ngOnInit(): void {
    console.info( "load list ")

    this.dashBoardsList = this.userDashboardService.getUserDashboards();
   }


}
