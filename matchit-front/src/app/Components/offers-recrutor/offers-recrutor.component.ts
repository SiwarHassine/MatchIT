import { Component, OnInit} from '@angular/core';
import { OfferService } from '../../Services/offers.service';
import { UsersService } from 'src/app/Services/users.service';
import {ApplicationsService} from '../../Services/applications.service';
import { NgForm, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Math } from '../../models/models';
import {CandidatsService} from '../../Services/candidats.service';
import { forkJoin } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { LoadingPageService } from '../../Services/loading-page.service';

declare var window: any ;

@Component({
  selector: 'app-offers-recrutor',
  templateUrl: './offers-recrutor.component.html',
  styleUrls: ['./offers-recrutor.component.css']
})
export class OffersRecrutorComponent implements OnInit {

  
  offers: any = [];
  displayedOffers: any = [];
  searchValue: string = '';
  formModal: any ;
  description: string = '';
  title: String = ''; 
  descriptionAdd: string = '';
  titleAdd: String = '';
  dateExpiration: String= '';
  offer: any;
  isUpdateMode: boolean = false;
  pageNumber = 0;
  pageSize = 4;
  totalPages= 0;
  filter: String = '';
  field = '';
  candidat: any;
  fileId: any[] = [];
  selectedOffer: any;  
  selectedPrediction: any;
  loading: boolean = false;  
  isLoadingPagination : boolean = false;


  // options
  applicationsByMonth :any[] = [];
  view: [number, number] = [660, 330];  
  showLegend: boolean = true;
  showLabels: boolean = true;
  gradient: boolean = false;
  isDoughnut: boolean = true;
  legendPosition: string = 'below';
  colorScheme = "fire";

  isLoading: boolean = false;

  constructor(private offerService: OfferService,
    private applicationService:ApplicationsService,
    private route: ActivatedRoute,private userservice: UsersService,
    private candidatService: CandidatsService,
    private fb: FormBuilder,
    public  loadingPageService: LoadingPageService) { }

  ngOnInit() {
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();
    
    this.offers = this.loadOffers();
    this.getApplicationsChart();    

  }

  loadOffers() {
    this.loadingPageService.setLoading(true);
    this.isLoadingPagination = this.loadingPageService.isLoading();

    console.log('Trying to load offers...');
    if (this.filter === 'expired') {
      this.field = 'true';
      this.loadingPageService.setLoading(false);
      this.isLoadingPagination = this.loadingPageService.isLoading();
    } else if (this.filter === 'notExpired') {
      this.field = 'false';
      this.loadingPageService.setLoading(false);
      this.isLoadingPagination = this.loadingPageService.isLoading();
    } else {


      this.offerService.getAllOffersPaginated(this.pageNumber, this.pageSize).pipe(
        switchMap((res: any) => {
          console.log('Received response from server:', res);
          this.offers = res['response']['content'];
          this.displayedOffers = this.offers;
          this.totalPages = res['response']['totalPages'];
  
          const applicationObservables = this.offers.map((offer: any) =>
            this.applicationService.getApplicationByIdOffer(offer.id)
          );
  
          return forkJoin(applicationObservables);
        })
      ).subscribe(
        (applications: any[]) => {
          applications.forEach((application: any, index: number) => {
            const offer = this.offers[index];
            offer.application = application;
            const userObservables = application.map((applicationItem: any) =>
              this.userservice.getUser(applicationItem.userId)
            );
  
            forkJoin(userObservables).subscribe((users: any[]) => {
              users.forEach((user: any, userIndex: number) => {
                application[userIndex].candidat = user;
  
                this.candidatService.getFileIdByUser(user.id).subscribe((fileIds: any) => {
                  application[userIndex].candidat.fileId = fileIds;
                  console.log("IDs des fichiers :", this.fileId);
                });
              });
            });
            const scores = application.map((applicationItem: any) => applicationItem.score);
            const scoresExp = application.map((applicationItem: any) => applicationItem.scoreExp);
            console.log("Scores :", scores);
            console.log("Scores Exp :", scoresExp);
          });
          this.offers.forEach((offer: any) => {
            console.log(offer);
          });
          this.loadingPageService.setLoading(false);
          this.isLoadingPagination = this.loadingPageService.isLoading();
        },
        (err: any) => {
          console.error('Error loading offers:', err);

      this.loadingPageService.setLoading(false);
      this.isLoadingPagination = this.loadingPageService.isLoading();
        }
      );

      this.loadingPageService.setLoading(false);
      this.isLoadingPagination = this.loadingPageService.isLoading();
    }
  
    this.offerService.getAllOffersByStatus(this.pageNumber, this.pageSize, this.field)
      .subscribe(
        (res: any) => {
          console.log('Received response from server:', res);
          this.offers = res['response']['content'];
          this.displayedOffers = this.offers;
          this.totalPages = res['response']['totalPages'];
          this.offers.forEach((offer: any) => {
            this.applicationService.getApplicationByIdOffer(offer.id).subscribe((application: any) => {
              offer.application=application;
            });
          });
        },
        (err: any) => {
          console.error('Error loading offers:', err);
        });
  }
  
  getPrediction(application: any) {
    this.loading = true;
  
    const data = {
      "score": application.score,
      "score_experience": application.scoreExp
    };
  
    this.candidatService.prediction(data).subscribe(
      (decision: any) => {
        application.prediction = decision;
        console.log("Prédiction :", application.prediction);
        this.loading = false;
      },
      (error: any) => {
        // Gérer les erreurs de prédiction
        console.error("Erreur de prédiction :", error);
        this.loading = false;
      }
    );
  }
  

  selectPrediction(prediction: any) {
    this.selectedPrediction = prediction;
  }
  
  deselectPrediction() {
    this.selectedPrediction = null;
  }

  showCandidatPostule(offer: any) {
    if (offer.selected) {
      
      offer.application.forEach((application : any )=>{
       this.getPrediction(application);
      })
      this.selectedOffer = offer;
    } else {
      this.selectedOffer = null;
  }
}

  setFilter(filter: string) {
    this.filter = filter;
    this.loadOffers();
  }
  
  showAddModal(){
    this.formModal = new window.bootstrap.Modal(
      document.getElementById("addOfferModal")
    );
    this.formModal.show();
  }

  showUpdateModal(id:any){
    console.log(id);
      this.formModal = new window.bootstrap.Modal(
      document.getElementById("updateOfferModal")
    );     
    this.route.paramMap.subscribe(params => {
      this.offerService.getOfferById(id)
        .subscribe((offer: any) => {
          this.offer = offer;
          this.title = offer.title;
          this.description = offer.description;
          this.dateExpiration = offer.dateExpiration;
          
        });
    });
    this.isUpdateMode = true;
}

showCandidatModal(offer: any){
  this.offer = offer;
  this.formModal = new window.bootstrap.Modal(
    document.getElementById("candidatModal")
  );
  this.formModal.show();
}

  addOffer(data: any) {
    this.offerService.addOffer(data).subscribe(
      (res: any) => {
        this.offerService.calculScoreAllFiles(res.id).subscribe(
          (res: any) => { 
            console.log(res);
          })
        console.log('Offer added successfully');
        this.formModal.hide();
        this.loadOffers();
      },
      (err: any) => {
        console.error('Error adding offer:', err);
      }
    );
  }

  getApplicationsChart() {
    this.applicationService.getApplicationsOrderByMonth().subscribe((data: any) => {
      const counts: { name: string; value: number; }[] = []; // Utilise un tableau d'objets pour stocker le nom du mois et le nombre d'applications
      data.forEach((application: any) => {
        if (application.dateCreation) {
          const parts = application.dateCreation.split('-');
          const year = parseInt(parts[0]);
          const month = parseInt(parts[1]);
          const day = parseInt(parts[2]);
          const dateCreation = new Date(year, month - 1, day);
          const monthKey = dateCreation.getMonth() + 1;
          const monthName = dateCreation.toLocaleString('default', { month: 'long' });
          const existingCount = counts.find(c => c.name === monthName);
          if (existingCount) {
            existingCount.value += 1;
          } else {
            counts.push({ name: monthName, value: 1 });
          }
        }
      });
      this.applicationsByMonth = counts;

    this.loadingPageService.setLoading(false);
    this.isLoading = this.loadingPageService.isLoading();
    });
  }
  
  
  onActivate(data: any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }
  onDeactivate(data: any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }
  onSelect(data: any): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  deleteOffer(id: any){
    this.offerService.deletOffer(id).subscribe(
      (res: any) => {
        console.log("offer deleted ");
        this.formModal.hide();
        this.loadOffers();
      },
      (err: any) => {
        console.error('Error deleting offer:', err)
      } 
    )
  }

  hideModel(){
    this.formModal.hide();
  }

  

    onSubmitAdd(offerForm: NgForm) {
      if (offerForm.valid) {
        const data = {
          title: offerForm.value.titleAdd,
          description: offerForm.value.descriptionAdd,
          dateExpiration: offerForm.value.dateExpiration
        };
        this.addOffer(data);
        offerForm.resetForm();
        
      }
    }

    showOfferDetails(offer: any){
      console.log(offer);
      this.offer = offer;
      this.formModal = new window.bootstrap.Modal(
        document.getElementById("offerDetailsModal")
      );
      this.formModal.show();
    }
  
    updateOffer(id: any, data: any) {
      this.offerService.updateOffer(id, data).subscribe(
        (res: any) => {
          console.log('Offer updated successfully');
          this.formModal.hide();
          this.loadOffers();
          this.isUpdateMode = false;
          
        },
        (err: any) => {
          console.error('Error updating offer:', err);
        }
      );
    }

  onSubmitUpdate(offerUpdateForm : NgForm){
    if (offerUpdateForm.valid) {
      const data = {
        title: offerUpdateForm.value.title,
        description: offerUpdateForm.value.description,
        dateExpiration: offerUpdateForm.value.dateExpiration,
      };
      const offerId = this.offer.id;
      this.updateOffer(offerId, data);
    }
  }
  
   cancelUpdate() {
    // annuler la mise à jour et désactiver le mode de mise à jour
    this.isUpdateMode = false;
  }
  
  

  onSearch() {
    console.log(this.searchValue);
    if (!this.searchValue) {
      this.displayedOffers = this.offers;
    } else {
      this.displayedOffers = this.offers.filter((offer: any) => {
        return offer.title && offer.title.toLowerCase().includes(this.searchValue.toLowerCase());
      });
    }
  }

  nextPage() {
    if (this.pageNumber < this.totalPages - 1) {
      this.pageNumber++;
      this.loadOffers();
    }
  }

  previousPage() {
    if (this.pageNumber > 0) {
      this.pageNumber--;
      this.loadOffers();
    }
  }

}
