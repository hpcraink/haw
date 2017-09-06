import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

import { AuthService } from '../auth.service';
import { User } from '../user.model';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html'
})

export class SignupComponent implements OnInit {
  myForm: FormGroup;

  constructor(private authService: AuthService, private fb: FormBuilder) {}

  onSubmit() {
    const user = new User(
      this.myForm.value.firstName,
      this.myForm.value.lastName,
      this.myForm.value.email,
      this.myForm.value.password,
      this.myForm.value.country,
      this.myForm.value.address,
      this.myForm.value.town,
      this.myForm.value.postcode,
      this.myForm.value.gender,
      this.myForm.value.uni
    );
    this.authService.signup(user)
      .subscribe(
        data => console.log(data),
        error => console.error(error)
      );
    this.myForm.reset();
  }

  ngOnInit() {
    console.log("Signup component invoked!");
    // build this.myForm with validators
    this.myForm = this.fb.group({
      firstName: new FormControl(null, Validators.required),
      lastName: new FormControl(null, Validators.required),
      email: new FormControl(null, [ Validators.required, Validators.email ]),
      password: new FormControl(null, Validators.required),
      confirmPwd: new FormControl(null, Validators.required),
      country: new FormControl(null, Validators.required),
      address: new FormControl(null, Validators.required),
      town: new FormControl(null, Validators.required),
      postcode: new FormControl(null, Validators.required),
      gender: new FormControl(null),
      uni: new FormControl(null, Validators.required)
    }, {validator: this.matchingPassword('password', 'confirmPwd')});
  }

  // function to check if password and confirmPwd are the same
  matchingPassword(passwordKey: string, confirmPwdKey: string) {
    return (group: FormGroup): {[key: string]: any} => {
      let password = group.controls[passwordKey];
      let confirmPwd = group.controls[confirmPwdKey];

      if (password.value !== confirmPwd.value) {
        return { mismatchedPasswords: true};
      };
    };
  };
};
