import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import { mergeMap } from 'rxjs/operators';
import { ActivatedRoute, Params } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import 'select2';
import { Select2OptionData } from 'ng-select2';
import { Options } from 'select2';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/Services/users.service';
import { PostulerService } from '../../Services/postuler.service';
import { user } from 'src/app/models/models';
import { FormGroup, FormControl } from '@angular/forms';
import { LoadingPageService } from '../../Services/loading-page.service';

declare var window: any;

@Component({
  selector: 'app-application-sp',
  templateUrl: './application-sp.component.html',
  styleUrls: ['./application-sp.component.css']
})
export class ApplicationSPComponent implements OnInit {
  isLoading: boolean = false;
  experiences: any;
  competences: any[] = [];
  user: any;
  form: FormGroup;
  application: any;
  applicationupdate: any = {
    "type": "POSTULER",
    "etat": "ENCOURS"
  }

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
  formModal: any;
  options: Options = {
    multiple: true
  };
  selectData: Select2OptionData[] = this.skills.map(skill => ({ id: skill, text: skill }));

  constructor(
    private userService: UsersService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private postulerService: PostulerService,
    private router: Router,
    public  loadingPageService: LoadingPageService

  ) {
  this.form = new FormGroup({
    experience: new FormControl(''),
  });
  }
  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('user')!);
    this.userService.getUser(this.user.id).subscribe(user => {
      console.log(user)
      if (user) {
        this.user = user;
      }
    });

    this.selectData = this.skills.map(skill => ({ id: skill, text: skill }));
    console.log(this.selectData)
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

 


  postulerOffer() {
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();
  
    const dataUP: any = {
      contents: this.competences.join(' '),
      experience: this.experiences
    };
  
    this.route.params.subscribe((params: Params) => {
      const idOffer = params['idOffer'];
      this.user = JSON.parse(sessionStorage.getItem('user')!);
  
      this.postulerService.findApplicationByofferUser(idOffer, this.user.id).subscribe(
        (data: any) => {
          this.application = data;
  
          if (this.competences.length === 0 && this.form.controls['experience'].value === '') {
            this.postulerService.postuler(this.application.id, this.applicationupdate).subscribe(
              () => {
                console.error('La candidature a été soumise avec succès', this.application.id);
                this.router.navigate(['applications']);
                this.loadingPageService.setLoading(false);
                this.isLoading = this.loadingPageService.isLoading();
              },
              (error: any) => {
                console.error('Erreur lors de la soumission de la candidature :', error);
                this.loadingPageService.setLoading(false);
                this.isLoading = this.loadingPageService.isLoading();
              }
            );
          } else {
            this.postulerService.getFileByUser(this.user.id).subscribe((fileId: any) => {
              console.log("dataaaaa", dataUP);
              this.postulerService.reCalculScore(fileId, idOffer, dataUP).subscribe(
                (res: any) => {
                  console.log('Réponse de reCalculScore :', res);
                  this.router.navigate(['applications']);
                  this.loadingPageService.setLoading(false);
                  this.isLoading = this.loadingPageService.isLoading();
                },
                (error: any) => {
                  console.error('Erreur lors de l\'appel à reCalculScore :', error);
                  this.loadingPageService.setLoading(false);
                  this.isLoading = this.loadingPageService.isLoading();
                }
              );
            });
          }
        },
        (error: any) => {
          console.error('Erreur lors de la soumission de la candidature :', error);
          this.loadingPageService.setLoading(false);
          this.isLoading = this.loadingPageService.isLoading();
        }
      );
    });
  }
  


  removeExperience(experience: any) {
    const index = this.experiences.findIndex((exp: any) => exp === experience);
    console.log(index)
    if (index !== -1) {
      this.experiences.splice(index, 1);
    }
  }

  hideModel() {
    this.formModal.hide()
  }
}

