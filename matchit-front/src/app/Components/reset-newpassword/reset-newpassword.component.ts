import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import zxcvbn from 'zxcvbn';
import { user } from 'src/app/models/models';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/Services/users.service';
import { PostulerService } from '../../Services/postuler.service';
import { LoadingPageService } from '../../Services/loading-page.service';

declare module 'zxcvbn';


@Component({
  selector: 'app-reset-newpassword',
  templateUrl: './reset-newpassword.component.html',
  styleUrls: ['./reset-newpassword.component.css']
})
export class ResetNewpasswordComponent implements OnInit {
  resetPasswordForm: FormGroup;
  isLoading: boolean = false;
  user!:user
  passwordStrength = 0;
  showPasswordStrength = false;
  hidePassword = true;
  NotValid=false;
  submitted = false;
  cvRequired = false;
  cvId : any;
  score : String = '';
  role: String='';
  constructor(private formBuilder: FormBuilder,private router:Router,
    private userservice: UsersService,
    private uploadservice : PostulerService,
    public  loadingPageService: LoadingPageService) { 
    this.resetPasswordForm = this.formBuilder.group({
      newPassword: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]]
    }, { validator: this.checkPasswords });
  }
  
  ngOnInit() {}
  
  onPasswordKeyUp() {
    const password = this.resetPasswordForm.controls['newPassword']?.value;
    if (password && password.length > 0) {
      this.showPasswordStrength = true;
      this.passwordStrength = this.getPasswordStrength(password);
    } else {
      this.showPasswordStrength = false;
      this.passwordStrength = 0;
    }
  }
  
  getPasswordStrength(password: string) {
    const result = zxcvbn(password);
    return result.score;
  }
  
  onSubmit() {
    if (this.resetPasswordForm.hasError('notSame')) {
      this.NotValid = true;
      return;
    }
    // Mettre à jour le mot de passe
    const newPassword = this.resetPasswordForm.controls['newPassword'].value;
    this.user=  JSON.parse(sessionStorage.getItem("user")!);
    this.user.password=newPassword;
    this.userservice.newpwd(this.user).subscribe({
      next:(data)=>{ 
         
         this.role = this.user.roles;
         if(this.role != 'CANDIDAT'){
           this.router.navigate(['home'])
         }
         this.submitted = true;
         this.cvRequired = this.resetPasswordForm.get('cvRequired')?.value;
      },
      error:(err)=>{console.log(err)
      }
  })
  
  }
 
  uploadCV(event: any) {
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();

    this.user = JSON.parse(sessionStorage.getItem('user')!);
    const file = event.target.files[0];
  
    this.uploadservice.uploadfile(this.user.id, file).subscribe(
      (data: any) => {
       this.cvId = data.cvId; // Accéder à l'ID du CV depuis la réponse

       this.uploadservice.calculScore(this.cvId).subscribe(
        (data: any) => {
          this.score = data;
         });
        console.log('CV uploaded successfully. CV ID:', this.cvId);
        this.router.navigate(['/home']);
      this.loadingPageService.setLoading(false);
      this.isLoading = this.loadingPageService.isLoading(); 
      },
      (error: any) => {
        console.error('Error uploading CV:', error);
        this.loadingPageService.setLoading(false);
        this.isLoading = this.loadingPageService.isLoading(); 
      }
    );
  }
  
  checkPasswords(group: FormGroup) {
    const newPassword = group.controls['newPassword'].value;      
    const confirmPassword = group.controls['confirmPassword'].value;
  
    return newPassword === confirmPassword ? null : { notSame: true };
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }


}