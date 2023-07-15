import { Component, OnInit, ViewChild, ComponentFactoryResolver, ViewContainerRef  } from '@angular/core';
import { CandidatsService } from '../../Services/candidats.service';
import { CV } from 'src/app/models/models';
import { ActivatedRoute, Params } from '@angular/router';
import { LoadingPageService } from '../../Services/loading-page.service';
import { PostulerService } from '../../Services/postuler.service';


@Component({
  selector: 'app-consult-candidat',
  templateUrl: './consult-candidat.component.html',
  styleUrls: ['./consult-candidat.component.css']
})
export class ConsultCandidatComponent implements OnInit {
  CV!: CV;
  competences: string[] = [];
  experiences: string[] = [];
  email: String = '';
  adresse: String = '';
  github: String = '';
  linkedIn: String = '';
  name: String = '';
  phone: String = '';
  isLoading: boolean = false;
  fileId: any;
  idapplication: any;
  applicationupdate: any;
  etat: any;
  from: any;
  isAcceptationValide: boolean = false;
  isRejetValide: boolean = false;

  constructor(private candidatService: CandidatsService,
    private route: ActivatedRoute,
    public loadingPageService: LoadingPageService,
    public postulerservice: PostulerService) {
    this.CV = new CV
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.idapplication = params['idapplication'];
      this.from = params['from'];
      this.etat = params['etat'];
      if (this.from == 'dashboard') {
        const userid = params['id'];
        this.postulerservice.getFileByUser(userid).subscribe((fileId: any) => {
          this.fileId = fileId;
          this.fetchCvContent(this.fileId);
        });
      } else if (this.from == 'offerrecrutor') {
        this.fileId = params['id'];
        this.fetchCvContent(this.fileId);
      }
    });
  }


  fetchCvContent(id: any): void {
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();
    console.log("saleeeeeeeem", id);
    this.candidatService.getFileCV(id).subscribe(
      (data: any) => {
        this.CV = data;
        if (data.contents) {
          this.competences = data.contents.split(',').map((c: any) => c.trim());
        }
        if (data.experience) {
          this.experiences = data.experience.split('.').map((e: any) => e.trim());
        }
        if (data.email) {
          this.email = data.email;
        }
        if (data.name) {
          this.name = data.name;
        }
        if (data.adresse) {
          this.adresse = data.adresse;
        }
        if (data.GitHub) {
          this.github = data.GitHub;
        }
        if (data.linkedIn) {
          this.linkedIn = data.linkedIn;
        }
        if (data.phone) {
          this.phone = data.phone;
        }

        this.loadingPageService.setLoading(false);
        this.isLoading = this.loadingPageService.isLoading();
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des données du CV.', error);

        this.loadingPageService.setLoading(false);
        this.isLoading = this.loadingPageService.isLoading();
      }
    );
  }


  acceptedCandidat() {
    this.applicationupdate = {
      "etat": "ACCEPTED"
    };
    this.postulerservice.postuler(this.idapplication, this.applicationupdate).subscribe(
      () => {
        console.log('La candidature a été soumise avec succès', this.idapplication);
        this.etat = "ACCEPTED";
        this.isRejetValide = false; 
        this.isAcceptationValide = true; 
      },
      (error: any) => {
        console.error('Erreur lors de la soumission de la candidature :', error);
      }
    );
  }

  rejectedCandidat() {
    this.applicationupdate = {
      "etat": "REJECTED"
    }

    this.postulerservice.postuler(this.idapplication, this.applicationupdate).subscribe(
      () => {
        console.log('La candidature a été soumise avec succès', this.idapplication);
        this.etat = "REJECTED";
        this.isAcceptationValide = false;
        this.isRejetValide = true; 
      },
      (error: any) => {
        console.error('Erreur lors de la soumission de la candidature :', error);
      }
    );

  }
}  
