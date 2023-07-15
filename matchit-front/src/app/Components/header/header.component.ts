import { Component, OnInit, AfterViewInit  } from '@angular/core';
import { UsersService } from 'src/app/Services/users.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, AfterViewInit  {
role: any;
user: any;
  constructor(private router:Router,
    private userservice: UsersService) {
    
   }

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('user')!);
    if(this.user){
    this.role = this.user.roles;
    console.log("role", this.role)
      }
    }

 submit() {
      this.userservice.logOut();
      this.router.navigate(['login']);
}
ngAfterViewInit(): void {
  const user = JSON.parse(sessionStorage.getItem('user')!);
  const loginLink = document.getElementById('loginLink');
  const logoutLink = document.getElementById('logoutLink');
  
  if (loginLink) {
    loginLink.addEventListener('click', function(event) {
      event.preventDefault();
      // Effectuer les actions de connexion
      // ...
  
      // Recharger la page
      location.reload();
    });
  }
  
  if (logoutLink) {
    logoutLink.addEventListener('click', function(event) {
      event.preventDefault();
      // Effectuer les actions de déconnexion
      // ...
  
      // Recharger la page
      location.reload();
    });
  }
   
    if (user) {
        // L'utilisateur est authentifié
        // Afficher le lien de déconnexion et masquer le lien de connexion
        if (loginLink) {
          loginLink.style.display = 'none'; // Masquer le lien de connexion
        }
        if (logoutLink) {
          logoutLink.style.display = 'block'; // Afficher le lien de déconnexion
        }
      } else {
        // L'utilisateur n'est pas authentifié
        // Afficher le lien de connexion et masquer le lien de déconnexion
        if (loginLink) {
          loginLink.style.display = 'block'; // Afficher le lien de connexion
        }
        if (logoutLink) {
          logoutLink.style.display = 'none'; // Masquer le lien de déconnexion
        }
      } 
  
}


}
