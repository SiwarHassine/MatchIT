import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
//import { ConfigService } from 'src/app/services/config.service';
//import { DataService } from 'src/app/services/data.service';
//import {RestService} from '../../services/rest.service';

@Component({
  selector: 'app-left-nav',
  templateUrl: './left-nav.component.html',
  styleUrls: ['./left-nav.component.css']
})
export class LeftNavComponent implements OnInit {

  /* tslint:disable:no-string-literal */
  LastUpdate: any;
  objectName = 'operation';
  attributeKey = 'lastUpdate';
  query = 'q=*';
  rowsNumber = 1;
  obj = null;
  showUpdate = false;
  dispUlParc = 'display:none';
  dispUlSetting = 'display:none';
  concepteur = true;
  isSuperUser = 0;
  cuid = '';
 // @Input() shrink: boolean;
  //@Output() changeShrink: EventEmitter<boolean> = new EventEmitter();
  //constructor(private config: ConfigService, private data: DataService, private rest: RestService) { }
  constructor() { }
  ngOnInit(): void{
   // if (localStorage.getItem('roles') && !localStorage.getItem('roles').toLowerCase().includes('concepteur')){
     // this.concepteur = false;
    //}
   // this.cuid = localStorage.getItem('userName');
    //this.shrink = false;
    //this.config.readConfig().subscribe(data => {
     // this.LastUpdate = data[this.attributeKey];
      //this.data.getDataSearchByObject(this.objectName, this.query, this.rowsNumber, 0, 0).subscribe(dataS => {
       // this.obj = dataS['results'][0][this.attributeKey];
      //});
    //});
    //if (this.isParc()){
     // this.dispUlParc = 'display:block';
   // }
    /*if (this.isSetting()){
      this.dispUlSetting = 'display:block';
    }*/
   // if (this.cuid.length === 8) {
     // this.getSuperUsers();
    //}
    //else{ // dans Integration et PreProd
      //this.isSuperUser = 1;
    //}
 // }
  //collapse(): void{
    //if (this.shrink === false)
    //{ this.shrink = true; }
    //else if (this.shrink === true) {
      //this.shrink = false;
    //}
    //this.changeShrink.emit(this.shrink);
 // }

  //toggle(clas = ''): void{
   // $('li ul.nav').hide();
    //if (clas){
      //$('li.' + clas + ' ul.nav').show();
   // }
  //}

  //isParc(): boolean {
   // return ['parc', 'prev', 'scenario', 'simule', 'optimise'].some(el => window.location.href.includes(el));
  //}

  //isSetting(): boolean {
   // return ['setting', 'cout'].some(el => window.location.href.includes(el));
  //}

  //getSuperUsers(): void {
   // this.rest.getUsers()
     // .subscribe({
       // next: data => {
         // this.isSuperUser = data.filter(elm => elm.role && elm.role.includes('superuser'))
           // .filter(elm => elm.cuid && elm.cuid.toLowerCase().includes(this.cuid.toLowerCase())).length;
        //}
      //});
  }
}
