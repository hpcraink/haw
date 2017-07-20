var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { routes } from './signup.routes';
import { SignupComponent } from './signup.component';
var SignupModule = (function () {
    function SignupModule() {
    }
    return SignupModule;
}());
SignupModule.routes = routes;
SignupModule = __decorate([
    NgModule({
        declarations: [
            SignupComponent
        ],
        imports: [
            CommonModule,
            ReactiveFormsModule,
            RouterModule.forChild(routes)
        ]
    })
], SignupModule);
export { SignupModule };
