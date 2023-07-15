import { Component } from '@angular/core';
import { LoadingPageService } from './Services/loading-page.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'matchit-view';

  constructor(private loadingService: LoadingPageService) {}
}
