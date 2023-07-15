import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
import { ProfilComponent } from './Components/profil/profil.component';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { OffersComponent } from './Components/offers/offers.component'
import { OfferDetailsComponent} from './Components/offer-details/offer-details.component'
import { ApplicationsComponent} from './Components/applications/applications.component'
import { ApplicationSPComponent } from './Components/application-sp/application-sp.component'
import { OffersRecrutorComponent } from './Components/offers-recrutor/offers-recrutor.component'
import { ConsultCandidatComponent } from './Components/consult-candidat/consult-candidat.component'
import { DashboardComponent } from './Components/dashboard/dashboard.component'
import { AdminComponent } from './Components/admin/admin.component';
import { ResetNewpasswordComponent } from './Components/reset-newpassword/reset-newpassword.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profil', component: ProfilComponent },
  { path: 'offers', component: OffersComponent },
  { path: 'offerDetails/:id', component: OfferDetailsComponent },
  { path: 'applications', component: ApplicationsComponent },
  { path: 'application/:idOffer', component: ApplicationSPComponent},
  { path: 'offers/Recrutor', component: OffersRecrutorComponent},
  { path: 'candidat/:id/:idapplication/:etat/:from', component: ConsultCandidatComponent },  
  { path: 'dashboard', component: DashboardComponent},
  { path: 'recruteurs', component: AdminComponent},
  { path: 'resetPassword', component: ResetNewpasswordComponent}
  // Autres routes
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
