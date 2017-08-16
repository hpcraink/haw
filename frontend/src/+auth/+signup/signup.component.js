var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { User } from '../user.model';
var SignupComponent = (function () {
    function SignupComponent(authService, fb) {
        this.authService = authService;
        this.fb = fb;
    }
    SignupComponent.prototype.onSubmit = function () {
        var user = new User(this.myForm.value.firstName, this.myForm.value.lastName, this.myForm.value.email, this.myForm.value.password, this.myForm.value.country, this.myForm.value.address, this.myForm.value.town, this.myForm.value.postcode, this.myForm.value.gender, this.myForm.value.uni);
        this.authService.signup(user)
            .subscribe(function (data) { return console.log(data); }, function (error) { return console.error(error); });
        this.myForm.reset();
    };
    SignupComponent.prototype.ngOnInit = function () {
        console.log("Signup component invoked!");
        // build this.myForm with validators
        this.myForm = this.fb.group({
            firstName: new FormControl(null, Validators.required),
            lastName: new FormControl(null, Validators.required),
            email: new FormControl(null, [Validators.required, Validators.email]),
            password: new FormControl(null, Validators.required),
            confirmPwd: new FormControl(null, Validators.required),
            country: new FormControl(null, Validators.required),
            address: new FormControl(null, Validators.required),
            town: new FormControl(null, Validators.required),
            postcode: new FormControl(null, Validators.required),
            gender: new FormControl(null),
            uni: new FormControl(null, Validators.required)
        }, { validator: this.matchingPassword('password', 'confirmPwd') });
    };
    // function to check if password and confirmPwd are the same
    SignupComponent.prototype.matchingPassword = function (passwordKey, confirmPwdKey) {
        return function (group) {
            var password = group.controls[passwordKey];
            var confirmPwd = group.controls[confirmPwdKey];
            if (password.value !== confirmPwd.value) {
                return { mismatchedPasswords: true };
            }
            ;
        };
    };
    ;
    return SignupComponent;
}());
SignupComponent = __decorate([
    Component({
        selector: 'app-signup',
        templateUrl: './signup.component.html'
    }),
    __metadata("design:paramtypes", [AuthService, FormBuilder])
], SignupComponent);
export { SignupComponent };
;
