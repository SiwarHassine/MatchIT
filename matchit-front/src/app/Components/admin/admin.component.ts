import { Component, OnInit } from '@angular/core';
import {UsersService} from '../../Services/users.service'
import {RegisterService} from '../../Services/register.service'
import { NgForm, FormGroup, FormControl,FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoadingPageService } from '../../Services/loading-page.service';
import { Math } from '../../models/models';

declare var window: any ;
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  recruteurs: any = [];
  isLoading: boolean = false;
  searchValue: string = '';
  displayRecruteur: any = [];
  formModal: any ;
  firstnameAdd: string = '';
  nameAdd: String = '';
  emailAdd: String ='';
  role: String='';
  registerForm = new FormGroup({
    firstname: new FormControl(''),
    name: new FormControl(''),
    email: new FormControl('', [Validators.required, Validators.email]),
  });
  recruteurForm: FormGroup;
  recruteur : any;
  email : String ='';
  firstname : String = '';
  name : String= '';
  phone : String='';
  genre: String='';
  isUpdateMode: boolean = false;
  idRecToDelete: any;
  pageNumber: number = 0; // Numéro de la page actuelle
  pageSize: number = 5; // Nombre d'éléments par page
  totalPages: number = 0; // Nombre total de pages
  displayedRecruteurs: any[] = [];
  allRecruteurs: any;
  ngOnInit() {
    this.recruteurs = this.getUsers();
    this.loadAllRecruteurs();

  }

  constructor(private usersService : UsersService,
              private registerService : RegisterService,
            private formBuilder: FormBuilder,
            private route: ActivatedRoute,
            public  loadingPageService: LoadingPageService) {
    this.recruteurForm = this.formBuilder.group({
         firstname: ['', Validators.required],
         name: ['', Validators.required],
         phone: ['', Validators.required],
         genre: ['', Validators.required],         
         email: ['', [Validators.required, Validators.email]],
          roles: ['RECRUTEUR']
        });
      }

 
  getUsers() {
        this.loadingPageService.setLoading(true);
        this.isLoading = this.loadingPageService.isLoading();
      
        this.usersService.getRecruteurs().subscribe((data: any) => {
          console.log('Received response from server:', data);
          this.recruteurs = data;
          this.totalPages = Math.ceil(this.recruteurs.length / this.pageSize); // Calcul du nombre total de pages
          this.displayedRecruteurs = this.recruteurs.slice(0, this.pageSize); // Obtenir les recruteurs à afficher sur la page actuelle
      
          this.loadingPageService.setLoading(false);
          this.isLoading = this.loadingPageService.isLoading();
        });
      }     

  loadAllRecruteurs(){
    this.usersService.getRecruteurs().subscribe((data: any) => {
      this.allRecruteurs = data;
    })

  }    
 

      nextPage() {
        if (this.pageNumber < this.totalPages - 1) {
          this.pageNumber++;
          this.getUsers();
        }
      }
    
      previousPage() {
        if (this.pageNumber > 0) {
          this.pageNumber--;
          this.getUsers();
        }
      }
      
  onSearch() {
    console.log(this.searchValue);
    if (!this.searchValue) {
      this.displayedRecruteurs = this.recruteurs.slice(0, this.pageSize);
    } else {
      this.displayedRecruteurs = this.allRecruteurs.filter((recruteur: any) => {
        return recruteur.firstname && recruteur.firstname.toLowerCase().includes(this.searchValue.toLowerCase());
      });
    }
  }


  showAddModal(){
    this.formModal = new window.bootstrap.Modal(
      document.getElementById("addRecModal")
    );
    this.formModal.show();
  }

  showDeleteModal(id: any){
    this.idRecToDelete = id;
    this.formModal = new window.bootstrap.Modal(
      document.getElementById("confirmDeleteModal")
    );
    this.formModal.show();
  }

  showUpdateModal(id:any){
    console.log(id);
    this.formModal = new window.bootstrap.Modal(
      document.getElementById("updateRec")
    );     
    this.route.paramMap.subscribe(params => {
       this.usersService.getUser(id)
        .subscribe((recruteur: any) => {
          this.recruteur = recruteur;
          console.log("recruteuraaaaa ", recruteur)
          this.firstname = recruteur.firstname;
          this.email = recruteur.email;
          this.name = recruteur.name;
          this.phone = recruteur.phone;
        });
    });
    this.isUpdateMode = true;
    this.formModal.show(); // ajout de cette ligne
}

onSubmitUpdate(RecruteurUpdateForm : NgForm){
  if (RecruteurUpdateForm.valid) {
    const data = {
      id: this.recruteur.id,
      firstname: RecruteurUpdateForm.value.firstname,
      name: RecruteurUpdateForm.value.name,
      email: RecruteurUpdateForm.value.email,
      phone: RecruteurUpdateForm.value.phone,
      datenaisance: this.recruteur.datenaisance
    };
    this.updateRecruteur(data);
  }
}
updateRecruteur(data: any) {
  this.usersService.updateRecruteur(data).subscribe(
    (res: any) => {
      console.log('Rec updated successfully');
      this.formModal.hide();
      this.getUsers();
      this.isUpdateMode = false;
      
    },
    (err: any) => {
      console.error('Error updating Rec:', err);
    }
  );
}

addRecruteur() {
    if (this.recruteurForm.controls['email'].invalid) {
      console.error('Email is required');
      return;
    }
  this.usersService.reg(this.recruteurForm.value).subscribe(
    (res: any) => {
        console.log('Rec added successfully');
        this.formModal.hide();
        this.getUsers();
        this.recruteurForm.reset(); // Réinitialiser le formulaire
        this.hideModel();
      },
      (err: any) => {
        console.error('Error adding rec:', err);
      }
    );
  }
  showRecruteurDetails(recruteur: any){
    console.log(recruteur);
    this.recruteur = recruteur;
    this.formModal = new window.bootstrap.Modal(
      document.getElementById("detailsModal")
    );
    this.formModal.show();
  }  
  hideModel(){
    this.formModal.hide();
  }

  deleteRecruteur(){
    this.usersService.delete(this.idRecToDelete).subscribe(
      (res: any) => {
        console.log("recruteur deleted ");
        this.formModal.hide();
        this.getUsers();
      },
      (err: any) => {
        console.error('Error deleting recruteur:', err)
      } 
    )
  }
}

