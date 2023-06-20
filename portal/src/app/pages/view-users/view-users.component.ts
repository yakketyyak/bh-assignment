import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user-service';
import {SearchRequest} from '../../models/search.request.model';
import {UserResponse} from '../../models/user.response';
import {Router} from '@angular/router';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.scss']
})
export class ViewUsersComponent implements OnInit  {
  page = 0;
  size = 5;
  userResponse: UserResponse;
  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadPage(page: number) {
    this.page = page;
    this.loadUsers();
  }

  loadUsers(){
    const request: SearchRequest = new SearchRequest();
    request.size = this.size;
    request.page = this.page !== 0 ? this.page - 1 : this.page;
    this.userService.getAll(request).subscribe(
        (userResponse: UserResponse) => {
          this.userResponse = userResponse;
          console.log(userResponse);
        }
    );
  }

  onCreateAccount(id){
    return this.router.navigate(['/accounts', id]);
  }

  onDetailsCustomer(id){
    return this.router.navigate(['/users', id, 'details']);
  }
}
