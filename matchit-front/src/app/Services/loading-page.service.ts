import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingPageService {
  private isLoadingB: boolean = false;

  setLoading(value: boolean) {
    this.isLoadingB = value;
  }

  isLoading(): boolean {
    return this.isLoadingB;
  }
}

