import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OfferService } from '../../Services/offers.service';
import { ProfileService } from '../../Services/profil.service';
import 'select2';
import { Select2OptionData } from 'ng-select2';
import { Options } from 'select2';
import { LoadingPageService } from '../../Services/loading-page.service';
import { PostulerService } from '../../Services/postuler.service';
declare var window: any ;

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  offer: any;
  user: any;
  firstname: string = '';
  lastname: string = '';
  email: string = '';
  phone: string = '';
  birthdate: string = '';
  formModal: any;
  experiences: any[] = [];
  educations: any[] = [];
  typeApplication: any;
  skills = [
    'Angular',
    'React',
    'JavaScript',
    'Python',
    'PHP',
    'Ruby',
    'Java',
    'C#',
    'C++',
    'CSS',
    'HTML',
  ];
  options: Options = {
    multiple: true
  };
  selectData: Select2OptionData[] = this.skills.map(skill => ({ id: skill, text: skill }));
  isLoading: boolean = false;
  constructor(
    private route: ActivatedRoute,
    private offerService: OfferService,
    private userService: ProfileService,
    public  loadingPageService: LoadingPageService,
    public postulerService : PostulerService
  ) { }

  ngOnInit() {
    this.user = JSON.parse(sessionStorage.getItem('user')!);
    this.getOffer();
  }
  
  getOffer() {
    this.route.paramMap.subscribe(params => {
      const offerId = params.get('id');
      this.loadingPageService.setLoading(true);
      this.isLoading = this.loadingPageService.isLoading();
  
      this.offerService.getOfferById(offerId).subscribe((offer: any) => {
        this.offer = offer;
        this.postulerService.findApplicationByofferUser(this.offer.id, this.user.id)
          .subscribe((application: any) => {
            this.typeApplication = application.type;
            console.log("type", this.typeApplication);
            this.loadingPageService.setLoading(false);
            this.isLoading = this.loadingPageService.isLoading();
          });
  
        this.loadingPageService.setLoading(false);
        this.isLoading = this.loadingPageService.isLoading();
      });
    });
  }
  
  
  showApplicationForm() {
    const userId = '1';
    this.userService.getProfile(userId).subscribe((currentUser: any) => {
      console.log('currentUser:', currentUser);
      this.user = currentUser;
      this.firstname = this.user.firstname;
      this.lastname = this.user.lastname;
      this.email = this.user.email;
      this.phone = this.user.phone;
      this.birthdate = this.user.birthdate;

      this.formModal.show();
    });
   
  }

  onApply() {
    // Handle the form submission here
    this.formModal.hide();
  }


  showAddExperienceModal() {
    this.formModal = new window.bootstrap.Modal(
    document.getElementById('add-experience-modal')
    );
    this.formModal.show();
    }
    
    showAddEducationModal() {
      this.formModal = new window.bootstrap.Modal(
      document.getElementById('add-education-modal')
      );
      this.formModal.show();
      }

  onAddExperience() {
    const experience = {
    id: this.experiences[this.experiences.length - 1].id + 1, 
    position: (<HTMLInputElement>document.getElementById('position')).value,
    campany: (<HTMLInputElement>document.getElementById('campany')).value,
    description: (<HTMLInputElement>document.getElementById('description')).value,
    };
    this.experiences.push(experience);
    console.log(this.experiences)
    this.formModal.hide();
    }
    
    onAddEducation() {
      const education = {
      id: this.educations[this.educations.length - 1].id + 1,
      diplome: (<HTMLInputElement>document.getElementById('diplome')).value,
      ecole: (<HTMLInputElement>document.getElementById('ecole')).value,
      date: (<HTMLInputElement>document.getElementById('date')).value,
      };
      this.educations.push(education);
      console.log(this.educations)
      this.formModal.hide();
      }
  hideModel(){
    this.formModal.hide();
  }
  removeExperience(experience: any) {
    const index = this.experiences.findIndex((exp: any) => exp === experience);
    console.log(index)
    if (index !== -1) {
      this.experiences.splice(index, 1);
    }
  }
  
  removeEducation(education: any) {
    const index = this.educations.findIndex((edu: any) => edu === education);
    console.log(index)
    if (index !== -1) {
      this.educations.splice(index, 1);
    }
  }
}
