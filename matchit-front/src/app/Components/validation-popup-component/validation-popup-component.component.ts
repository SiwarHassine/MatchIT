import { Component, OnInit, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-validation-popup-component',
  templateUrl: './validation-popup-component.component.html',
  styleUrls: ['./validation-popup-component.component.css']
})
export class ValidationPopupComponentComponent implements OnInit {

  @Output() close = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  closeDialog(): void {
    this.close.emit();
  }

}
