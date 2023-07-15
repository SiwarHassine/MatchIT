import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  //roles = '';
  //envNonGassi = ['integration', 'staging', 'localhost'];
  constructor(private router: Router) {
    // empty constructor
  }

  ngOnInit(): void{
   // if (localStorage.getItem('roles')){
     // this.roles = localStorage.getItem('userName') + ' ' + (localStorage.getItem('roles').toLowerCase().includes('concepteur') ? 'CONCEPTEUR' : 'CONSULTATION');
    //}
   // else if (this.envNonGassi.filter(elm => window.location.href.toLowerCase().includes(elm)).length === 0) {
      // location.reload();
     // this.router.navigate(['']).then(r => {});
    //}
  }
}
