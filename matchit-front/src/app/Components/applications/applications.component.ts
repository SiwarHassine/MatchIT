import { Component, OnInit } from '@angular/core';
import { ApplicationsService } from '../../Services/applications.service';
import { user } from 'src/app/models/models';
import { OfferService } from '../../Services/offers.service';
import { LoadingPageService } from '../../Services/loading-page.service';
import { Math } from '../../models/models';

@Component({
  selector: 'app-applications',
  templateUrl: './applications.component.html',
  styleUrls: ['./applications.component.css']
})
export class ApplicationsComponent implements OnInit {
  applications: any;
  isLoading: boolean = false;
  user:any;
  selectedApplication: any;  
  applicationPostulated : any;
  loading: boolean = false; 

  pageNumber = 0;
  pageSize = 5;
  totalPages= 0;

  view: [number, number] = [700, 400];
  // options
  gradient: boolean = false;
  animations: boolean = true;

  colorScheme = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5']
  };



  
  constructor(private applicationsService: ApplicationsService,
    private offerervice: OfferService,
    public  loadingPageService: LoadingPageService) { }

  ngOnInit() {
    this.getApplications();
  }


  getApplications() {
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();
    
    this.user=  JSON.parse(sessionStorage.getItem("user")!);
    this.applicationsService.getApplicationByUserIdPaginated(this.user.id, this.pageNumber, this.pageSize)
    .subscribe((data: any) => {
      this.applications = data;
      this.applications.forEach((application: any) => {    
        // Add the offer object to the application
        this.offerervice.getOfferById(application.idOffer).subscribe((offer: any) => {
          application.offer = offer;
         
          this.loadingPageService.setLoading(false);
          this.isLoading = this.loadingPageService.isLoading();
        });
     
      });
      this.loadingPageService.setLoading(false);
      this.isLoading = this.loadingPageService.isLoading();
    });
    this.applicationsService.getApplicationByUserId(this.user.id)
    .subscribe((data: any) => { 
      this.totalPages = Math.ceil(data.length / this.pageSize);
      
    })
    
  }

  showApplicationDetails(application: any){
   
    if (application.selected) {
      this.loading = true;
       const offerId = application.idOffer;
       console.log("offerId", offerId)
       this.applicationsService.getApplicationByIdOffer(offerId).subscribe((application: any) => {
        this.applicationPostulated=application;
        console.log("application", this.applicationPostulated)
        this.loading = false;
      });
      
      this.selectedApplication = application;
    } else {
      this.selectedApplication = null;
      this.applicationPostulated = null;
  }
}

deleteApplication(id: any) {
  this.loadingPageService.setLoading(true);
  this.isLoading = this.loadingPageService.isLoading();

  this.applicationsService.deleteApplication(id).subscribe(
    () => {
      this.getApplications();
      console.log("Application deleted");
      this.loadingPageService.setLoading(false);
      this.isLoading = this.loadingPageService.isLoading();
    },
    (err: any) => {
      console.error('Error deleting application:', err);
       }  
  );
  this.loadingPageService.setLoading(false);
  this.isLoading = this.loadingPageService.isLoading();
}


nextPage() {
  if (this.pageNumber < this.totalPages - 1) {
    this.pageNumber++;
    this.getApplications();
  }
}

previousPage() {
  if (this.pageNumber > 0) {
    this.pageNumber--;
    this.getApplications();
  }
}

}
