import { Placeholder } from '@angular/compiler/src/i18n/i18n_ast';
import { Component, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { user, userauth } from 'src/app/models/models';
import { UsersService } from 'src/app/Services/users.service';
import { LoadingPageService } from '../../Services/loading-page.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLoading: boolean = false;

 userauth!: userauth;
 user!:user;
  departement:any;
  loginForm!: FormGroup;
  hasError!: boolean;
 @Output()
  hide=true
  messageSuccess!:string;
  messageError!:string;
  spinner=false;
  errorMessage: any;
  constructor(private fb: FormBuilder,
    private router:Router,
    private userservice: UsersService,
    private location: Location,
    public  loadingPageService: LoadingPageService
    ) {this.userauth=new userauth 
    this.user=new user}

  ngOnInit(): void {
this.initForm()
  }
  get f() {
    return this.loginForm.controls;
  }
  submit(){
    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();

    this.userservice.auth(this.userauth).subscribe({
      next:(data)=>{ 
        this.user=data;
        this.spinner=!this.spinner;
        if(this.user.active===0){
         this.router.navigate(['resetPassword'])
        }
        else{
          this.router.navigate(['home']).then(() => {
            location.reload();
          });
        }
        this.loadingPageService.setLoading(false);
        this.isLoading = this.loadingPageService.isLoading(); 
      },
      error:(err)=>{console.log(err)
        this.spinner=!this.spinner;
        this.initForm();
        if (err) {
          // Le serveur a renvoyé une réponse 401 Unauthorized, ce qui signifie que les informations de connexion sont incorrectes
          this.errorMessage='Invalid login credentials';
        }
        this.loadingPageService.setLoading(false);
        this.isLoading = this.loadingPageService.isLoading(); 
      }
      
  })

  }
initForm() {
  this.loginForm = this.fb.group(
    {
      email: [
        null,
        Validators.compose([
          Validators.required,
          //Validators.minLength(8),
          //Validators.maxLength(8), // https://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address
        ]),
      ],
     
      password: [
        null,
        Validators.compose([    
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(68),
          //Validators.pattern(/^(?=.*\d)(?=.*[a-z]).{8,68}$/)
        ]),
      ],
      
   /*   emailVerificationUrl: [
      //  environment.email_verification_url,
      null,
        Validators.compose([
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(500),
          Validators.required

        ]),
      ],*/

      //REQUIREDTRUE NOT REQUIRED
      
     
    },
    {
    
    }
  );
 
}


}
