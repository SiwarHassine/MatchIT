import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgSelect2Module } from 'ng-select2';
import { CommonModule } from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';

//import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './Components/footer/footer.component';
import { HeaderComponent } from './Components/header/header.component';
import { HomeComponent } from './Components/home/home.component';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';
import { ProfilComponent } from './Components/profil/profil.component';
import { OffersComponent } from './Components/offers/offers.component';
import { OfferDetailsComponent } from './Components/offer-details/offer-details.component'; 
import { ToastrModule } from 'ngx-toastr';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ApplicationsComponent } from './Components/applications/applications.component';
import { ApplicationSPComponent } from './Components/application-sp/application-sp.component';
import { OffersRecrutorComponent } from './Components/offers-recrutor/offers-recrutor.component';
import { ConsultCandidatComponent } from './Components/consult-candidat/consult-candidat.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
//import {HeaderComponent} from './Components/layout/header/header.component';
//import {FooterComponent} from './Components/layout/footer/footer.component';
import {LeftNavComponent} from './Components/layout/left-nav/left-nav.component'
import { InterceptorService } from './Services/interceptor.service';
import { AdminComponent } from './Components/admin/admin.component';
import { ResetNewpasswordComponent } from './Components/reset-newpassword/reset-newpassword.component';
import { LoadingPageComponent } from './Components/loading-page/loading-page.component';
import { ValidationPopupComponentComponent } from './Components/validation-popup-component/validation-popup-component.component';




@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ProfilComponent,
    OffersComponent,
    OfferDetailsComponent,
    ApplicationsComponent,
    ApplicationSPComponent,
    OffersRecrutorComponent,
    ConsultCandidatComponent,
    DashboardComponent,
    LeftNavComponent,
    AdminComponent,
    ResetNewpasswordComponent,
    LoadingPageComponent,
    ValidationPopupComponentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    BsDropdownModule,
    BsDropdownModule.forRoot(),
    NgSelect2Module,
    CommonModule,
    TranslateModule.forRoot(),
   // NgxChartsModule
   NgxChartsModule,
    
  ],
  providers: [
    InterceptorService,
    {provide:HTTP_INTERCEPTORS,useClass:InterceptorService,multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
