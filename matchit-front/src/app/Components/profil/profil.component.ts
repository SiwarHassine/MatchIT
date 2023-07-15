import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsersService } from 'src/app/Services/users.service';;
import { user } from 'src/app/models/models';
import { LoadingPageService } from '../../Services/loading-page.service';
import { PostulerService } from '../../Services/postuler.service';
import {CandidatsService} from '../../Services/candidats.service'


@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {
  profileForm!: FormGroup;
  isLoading: boolean = false;
  id!: string;
  user!:user;
  submitted = false;
  error = false;
  success = false;
  profil: any;
  showForm = false;
  showFormCV = false;
  showFormPass = false;
  resetPasswordForm: FormGroup;
  idFile : any;
  successMessage: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private UsersService: UsersService,
    private route: ActivatedRoute,
    private router: Router,
    private uploadservice : PostulerService,
    public  loadingPageService: LoadingPageService,
    private candidatsService : CandidatsService
  ) {  this.resetPasswordForm = this.formBuilder.group({
    newPassword: ['', [Validators.required, Validators.minLength(8)]]
  });
}

  ngOnInit() {

    this.user=  JSON.parse(sessionStorage.getItem("user")!);
    this.showForm = false;
    this.showFormCV = false;
    this.showFormPass = false;
    this.route.params.subscribe(params => {
      this.profileForm = this.formBuilder.group({
        firstname: ['', Validators.required],
        name: ['', Validators.required],
        email: ['', Validators.required],
        phone: ['', Validators.required],
        birthdate: ['', Validators.required]
      });
   this.getProfile()
    });
  }

  getProfile() {
    this.user=  JSON.parse(sessionStorage.getItem("user")!);
    this.profil=this.user;
    if(this.user!=null){
      this.profileForm.setValue({
        firstname: this.user.firstname,
        name: this.user.name,
        email: this.user.email,
        phone: this.user.phone,
        birthdate: this.user.datenaisance
      });
    }
  }

  get f() { return this.profileForm.controls; }

  onSubmit() {
    this.submitted = true;
  
    if (this.profileForm.invalid) {
      return;
    }

    const user = JSON.parse(sessionStorage.getItem('user')!);
    const updatedUser = {
      ...user,
      firstname: this.profileForm.value.firstname,
      name: this.profileForm.value.name,
      email: this.profileForm.value.email,
      phone: this.profileForm.value.phone,
      datenaisance: this.profileForm.value.birthdate,
    };
  
    // Appeler la méthode de mise à jour de l'utilisateur dans le service
    this.UsersService.update(updatedUser)
    .subscribe((res: any) => {
      // Mettre à jour les données de l'utilisateur dans le Local Storage
      sessionStorage.setItem('user', JSON.stringify(updatedUser));
      this.success = true;
      this.error = false;
      this.showForm = false;
      this.getProfile(); 
    }, (err:any) => {
      this.error = true;
      this.success = false;
      console.log(err);
    });
  }

  onSubmitPass() {
    
    // Mettre à jour le mot de passe
    const newPassword = this.resetPasswordForm.controls['newPassword'].value;
    this.user=  JSON.parse(sessionStorage.getItem("user")!);
    this.user.password=newPassword;
    this.UsersService.newpwd(this.user).subscribe({
      next:(data)=>{
        this.success = true;
        this.error = false;
        this.showFormPass = false;
        this.getProfile();  
       
      },
      error:(err)=>{console.log(err)
      }
  })
}

updateCV(event: any) {
  this.loadingPageService.setLoading(true);
  this.isLoading = this.loadingPageService.isLoading();

  this.user = JSON.parse(sessionStorage.getItem('user')!);
  const file = event.target.files[0];
  this .candidatsService.getFileIdByUser(this.user.id).subscribe(
    (id : any) => {
      this.idFile = id;
      this.UsersService.updateCV(this.idFile, file).subscribe(
        (data: any) => {
          if (data.status === 200) {
            this.successMessage = 'Le CV a été mis à jour avec succès.';
          }
    
         this.uploadservice.calculScore(this.idFile).subscribe(
          (data: any) => {
           });
          
          this.router.navigate(['/home']);
        this.loadingPageService.setLoading(false);
        this.isLoading = this.loadingPageService.isLoading(); 
        }

      )}
  ,
    (error: any) => {
      console.error('Error uploading CV:', error);
      this.loadingPageService.setLoading(false);
      this.isLoading = this.loadingPageService.isLoading(); 
    }
  );
}

  showFormf() {
    this.showForm = true;
  }

  showFormfCV() {
    this.showFormCV = true;
  }

  showFormfPass() {
    this.showFormPass = true;
  }

  hideForm() {
    this.showForm = false;
  }
}
