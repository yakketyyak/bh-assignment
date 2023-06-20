import { Component, OnInit } from '@angular/core';
import {AccountService} from '../../services/account-service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountCreation} from '../../models/account-creation.model';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent implements OnInit {

  accountCreationForm!: FormGroup;
  customerID: string;
  constructor(private accountService: AccountService,
              private formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe( params => this.customerID = params.id );
    this.accountCreationForm = this.formBuilder.group({
      initialCredit: ['', Validators.required]
    });
  }

  create(){
    const accountForm: AccountCreation = new AccountCreation();
    accountForm.customerID = this.customerID;
    accountForm.initialCredit = this.accountCreationForm.get('initialCredit').value;
    this.accountService.create(accountForm).subscribe(
      (account: Account) => {
        return this.router.navigate(['/users']);
      });
  }

  onHome(){
    return this.router.navigate(['/users']);
  }
}
