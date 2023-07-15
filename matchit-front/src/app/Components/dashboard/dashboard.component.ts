import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { DashboardService } from '../../Services/dashboard.service';
import { ApplicationsService } from '../../Services/applications.service';
import { CandidatsService} from '../../Services/candidats.service';
import { OfferService } from '../../Services/offers.service';
import {UsersService} from '../../Services/users.service';
import { Math } from '../../models/models';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { LoadingPageService } from '../../Services/loading-page.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  @ViewChild('chartsContainer', { static: false }) chartsContainer!: ElementRef;

  score: any = [];
  applications: any ;
  displayedApplication: any;
  candidats: any;
  offers: any;
  offer:any;
  user:any;
  isLoading: boolean = false;
  isLoadingPagination: boolean = false;
  pageNumber = 0;
  pageSize = 5;
  totalPages= 0;

  //options
  genreChart: any[] = [];
  view: [number, number] = [500, 370];  
  showLegend: boolean = true;
  showLabels: boolean = true;
  gradient: boolean = false;
  isDoughnut: boolean = true;
  legendPosition: string = 'right';
  colorScheme = "picnic";

    // options
    genreChartBar: { name: string, value: number }[] = [];
    viewBar: [number, number] = [750, 370];
    legendTitle: string = 'Age';
    legendTitleMulti: string = 'Months';
    legendPositionBar: string = 'below'; // ['right', 'below']
    legend: boolean = true;
    xAxis: boolean = true;
    yAxis: boolean = true;
    yAxisLabel: string = 'Candidats';
    xAxisLabel: string = 'Age';
    showXAxisLabel: boolean = true;
    showYAxisLabel: boolean = true;
    maxXAxisTickLength: number = 30;
    maxYAxisTickLength: number = 30;
    trimXAxisTicks: boolean = false;
    trimYAxisTicks: boolean = false;
    rotateXAxisTicks: boolean = false;
    xAxisTicks: any[] = ['22-25', '25-30', '30-40', '40-50']
    yAxisTicks: any[] = [22, 50,75 ,100, 120, 150]
    animations: boolean = true; // animations on load
    showGridLines: boolean = true; // grid lines
    showDataLabel: boolean = true; // numbers on bars
    gradientBar: boolean = false;
    colorSchemeBar ="fire";
    schemeType: string = 'ordinal'; // 'ordinal' or 'linear'
    activeEntries: any[] = ['book']
    barPadding: number = 5
    tooltipDisabled: boolean = false;
    yScaleMax: number = 200;
    roundEdges: boolean = false;

  constructor(private dashboardService: DashboardService, 
              private applicationsService: ApplicationsService,
              private candidatsService: UsersService,
              private offerervice: OfferService,
              public  loadingPageService: LoadingPageService) {   }

  ngOnInit(): void {
   // this.findScore()

  
    this.getCandidats();
    this.getOffers();
    this.getCandidatsChart();
    this.getCandidatsChartBar()
    this.loadingPageService.setLoading(true);
    this.isLoadingPagination = this.loadingPageService.isLoading();
    this.getApplicationsPaginated();
    this.getApplications();
  //  this.offersChart = this.offers.map((item:any) => [item.title, item.description]);
  }

/*  findScore(){
    this.dashboardService.getScore()
    .subscribe((res: any) => {
        console.log('Received response from server:', res);
        this.score=res;
      },
      (err: any) => {
        console.error('Error loading offers:', err);
      }
    );
  }
*/

getApplications() {

  this.applicationsService.getApplications().subscribe((data: any) => {
    data.sort((a: any, b: any) =>
      (b.average ? b.score.toString() : '').localeCompare(a.score ? a.score.toString() : '')
    );
    this.applications = data;
    this.totalPages = Math.ceil(this.applications.length / this.pageSize);
    this.applications.forEach((application: any) => {
      this.candidatsService.getUser(application.userId).subscribe((user: any) => {
        application.user = user;
      });
  
      this.offerervice.getOfferById(application.idOffer).subscribe((offer: any) => {
        application.offer = offer;
      });
    });
  });
  
}

getApplicationsPaginated() {
  this.applicationsService.getAllApplicationPaginated(this.pageNumber, this.pageSize).subscribe((data: any) => {
    if (Array.isArray(data.response.content)) {
      data.response.content.sort((a: any, b: any) =>
        (b.score ? b.score.toString() : '').localeCompare(a.score ? a.score.toString() : '')
      );
      const app = data.response.content;
      this.displayedApplication = app.slice(0, this.pageSize);

      this.displayedApplication.forEach((application: any) => {
        // Add the user object to the application
        this.candidatsService.getUser(application.userId).subscribe((user: any) => {
          application.user = user;
        });

        // Add the offer object to the application
        this.offerervice.getOfferById(application.idOffer).subscribe((offer: any) => {
          application.offer = offer;
        });
      });
      this.loadingPageService.setLoading(false);
      this.isLoadingPagination = this.loadingPageService.isLoading();
    } else {
      // Handle the case when data.response.content is not an array
    }

    this.loadingPageService.setLoading(false);
    this.isLoadingPagination = this.loadingPageService.isLoading();
  });
}

nextPage() {
  if (this.pageNumber < this.totalPages - 1) {
    this.pageNumber++;
    this.getApplicationsPaginated();
  }
}

previousPage() {
  if (this.pageNumber > 0) {
    this.pageNumber--;
    this.getApplicationsPaginated();
  }
}

  getCandidats() {
    this.candidatsService.getCandidats().subscribe((data: any) => {
      this.candidats = data;
      console.log(data)
    });
  }

  getOffers() {
    this.offerervice.getOffers().subscribe((data: any) => {
      this.offers = data;
      console.log("offers ",data)
    });
  }

 // getCandidatsChart() {
  //  this.candidatsService.getCandidats().subscribe((data: any) => {
   //   this.offers = data;
    //  console.log(data);
     // this.genreChart = data.map((candidat: any) => {
      //  return { name: candidat.genre, value: count(genre) };
     // });
    //});
  //}

  getCandidatsChart() {
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();

    this.candidatsService.getCandidats().subscribe((data: any) => {
      console.log(data);
      const counts: Record<string, any> = {};
      data.forEach((candidat: any) => {
        counts[candidat.genre] = counts[candidat.genre] ? counts[candidat.genre] + 1 : 1;
      });
      this.genreChart = Object.keys(counts).map((genre) => {
        return { name: genre, value: counts[genre] };
      });
      this.loadingPageService.setLoading(false);
      this.isLoading = this.loadingPageService.isLoading();
    });
  }

  getCandidatsChartBar() {
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();

    this.candidatsService.getCandidats().subscribe((data: any) => {
      console.log(data);
      const counts: Record<string, any> = {};
      data.forEach((candidat: any) => {
        const age = new Date().getFullYear() - new Date(candidat.datenaisance).getFullYear();
        counts[age] = counts[age] ? counts[age] + 1 : 1;
      });
      this.genreChartBar = Object.keys(counts).map((age) => {
        return { name: age, value: counts[age] };
      });
      this.loadingPageService.setLoading(false);
      this.isLoading = this.loadingPageService.isLoading();
    });
  }
  
  downloadPDF() {
    const doc = new jsPDF();

    const container = this.chartsContainer.nativeElement;

    html2canvas(container).then((canvas: any) => {
      const imgData = canvas.toDataURL('image/png');

      doc.addImage(imgData, 'PNG', 10, 10, 190, 100); 
      doc.save('dashboard.pdf');
    });
  }
  
}
