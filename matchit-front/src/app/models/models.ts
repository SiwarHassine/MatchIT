export class user{

    id!:number;
    firstname:string;
    name: string;
    datenaisance:string;
    phone: number;
    password!:string;
    email:string;
    roles:string;
    active: number;
    genre:string;
    candidattype!:string;
    constructor()
    {   
        this.firstname="";
        this.name="";
        this.datenaisance="";
        this.email="";
        this.roles="";
        this.active=0;
        this.phone=0;
        this.genre="";
    }
}
export class userauth{
    email!:string;
    password!:string;
}

export class CV {
    phone: string= '';
    contents: string ='';
    GitHub: string = '';
    name: string = '';
    adresse: string = '';
    id: string = '';
    linkedIn: string = '';
    experience: string ='';
    email: string ='';
  }

 export interface Math {
    log10(x: number): number;
}