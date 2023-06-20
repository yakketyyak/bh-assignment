import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthRequest} from '../../models/auth-resuest.model';
import {Router} from '@angular/router';
import {LoginService} from '../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private loginService: LoginService) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(){
    const request: AuthRequest = new AuthRequest();
    request.username = this.loginForm.get('username').value;
    request.password = this.loginForm.get('password').value;
    this.loginService.login(request).subscribe(
      (response: any) => {
        const data = {
          access_token: response.access_token
        };
        localStorage.setItem('access_token', JSON.stringify(data));
        return this.router.navigate(['/users']);
      });
  }

}
