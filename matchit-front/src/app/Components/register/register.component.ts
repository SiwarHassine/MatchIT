import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RegisterService } from '../../Services/register.service';
import { ToastrService } from 'ngx-toastr';
import { user } from 'src/app/models/models';
import { UsersService } from 'src/app/Services/users.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoadingPageService } from '../../Services/loading-page.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user!:user;
  registerForm = new FormGroup({
    firstname: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
    phone: new FormControl('', Validators.required),
    datenaisance: new FormControl('', Validators.required),
    genre: new FormControl('', Validators.required),
    candidattype: new FormControl('', Validators.required),
    roles: new FormControl('CANDIDAT', Validators.required),
  });

  isLoading: boolean = false;
  errorOccurred: any;
  errorMessage: any;

  cv: File | null = null;

  constructor(    private router:Router,
    private userservice: UsersService, private toastr: ToastrService,
    private route: ActivatedRoute,
    public  loadingPageService: LoadingPageService) {
      this.user=new user
    }

  onFileSelected(event: any) {
    this.cv = event.target.files[0];
  }

  get email() { return this.registerForm.get('email') as FormControl; }

  // Typescript Code
  signupdata(): void {

    this.loadingPageService.setLoading(true);
    this.isLoading = this.loadingPageService.isLoading();

    console.log(this.registerForm.value);
    this.user = this.registerForm.value;
  
    const formData = new FormData();
  
    this.userservice.reg(this.registerForm.value).subscribe(
      (res: any) => {
        this.toastr.success('Data added successfully');
        this.registerForm.reset();
        this.router.navigate(['login'])

      },
       (err: any) => {
      if (err.status === 400) {
        this.errorMessage = 'Invalid data provided. Please check your input and try again.';
      } else if (err.status === 500) {
        this.errorMessage ='A server error occurred. Please try again later.';
      } else {
        this.errorMessage ='Email exist. Please try again later.';
      }
      this.loadingPageService.setLoading(false);
      this.isLoading = this.loadingPageService.isLoading();
    }
  );
  }
  

}
