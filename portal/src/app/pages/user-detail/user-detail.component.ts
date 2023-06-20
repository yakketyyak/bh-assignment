import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../services/user-service';
import {User} from '../../models/user.model';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {

  user: User;
  customerID: string;
  constructor( private router: Router,
               private route: ActivatedRoute,
               private userService: UserService) { }

  ngOnInit(): void {
    this.route.params.subscribe( params => this.customerID = params.id );
    this.getById(this.customerID);
  }

  getById(id){
    this.userService.getById(id).subscribe(
      (user: User) => {
        this.user = user;
      });
  }
  onHome(){
    return this.router.navigate(['/users']);
  }
}
