import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; 

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule,RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
    // Carica lo stato pinnato dal localStorage
    const savedPinnedState = localStorage.getItem('sidebarPinned');
    if (savedPinnedState) {
      //this.isPinned = JSON.parse(savedPinnedState);
      //this.isExpanded = this.isPinned;
    }
  }
}
