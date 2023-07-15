import { Component, OnInit } from '@angular/core';
import { OfferService } from '../../Services/offers.service';
import { PaginationControlsDirective } from 'ngx-pagination';
import { Math } from '../../models/models';
import { LoadingPageService } from '../../Services/loading-page.service';




@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {
  offers: any = [];
  offersAll: any = [];
  displayedOffers: any = [];
  searchValue: string = '';
  pageNumber = 0;
  pageSize = 5;
  totalPages= 0;
  isLoading: boolean = false;
  constructor(private offerService: OfferService,
    public  loadingPageService: LoadingPageService) { }

  ngOnInit() {
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading(); 
    //this.offersAll = this.loadOffers();
    this.loadOffers()
    this.offers = this.getOffers();
    
  }

  loadOffers() {
    
    console.log('Trying to load offers...');
    this.offerService.getAllOffersNotExpired()
    .subscribe((res: any) => {
      this.offersAll = res;
      this.totalPages = Math.ceil(res.length / this.pageSize);
      },
      (err: any) => {
        console.error('Error loading offers:', err);
      }
      
    );
  }
 
  getOffers() {
    this.offerService.getAllOffersNotExPaginated(this.pageNumber, this.pageSize).subscribe((data:any) => {
      this.offers = data.response.content;
      this.displayedOffers = this.offers.slice(0, this.pageSize);
      
      this.loadingPageService.setLoading(false);
      this.isLoading = this.loadingPageService.isLoading(); 
    });
  
    
  }
  
  nextPage() {
    if (this.pageNumber < this.totalPages - 1) {
      this.pageNumber++;
      this.getOffers();
    }
  }

  previousPage() {
    if (this.pageNumber > 0) {
      this.pageNumber--;
      this.getOffers();
    }
  }


  onSearch() {
    console.log(this.searchValue);
    if (!this.searchValue) {
      this.displayedOffers = this.offers.slice(0, this.pageSize);
    } else {
      this.displayedOffers = this.offersAll.filter((offer: any) => {
        return offer.title && offer.title.toLowerCase().includes(this.searchValue.toLowerCase());
      });
      this.pageNumber = 1; // Réinitialiser à la première page
    }
  }

  // onPageChange(pageNumber: number) {
   // this.currentPage = pageNumber;
    //this.loadOffers();
  //}

  
}
