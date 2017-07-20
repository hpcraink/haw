import { Http, Response, Headers } from '@angular/http';
import { Injectable, EventEmitter } from '@angular/core';
import { Observable } from "rxjs/Observable";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Message } from './message.model';
import { ErrorService } from '../errors/error.service';

@Injectable() // to be able to inject Http service into this class
export class MessageService {
  private messages: Message[] = [];
  messageIsEdit = new EventEmitter<Message>();
  private host: string = "https://localhost:3000/message";

  constructor(private http: Http, private errorService: ErrorService) {}

  addMessage(message: Message) {
    const body = JSON.stringify(message);
    const headers = new Headers({'Content-Type': 'application/json'});
    const token = localStorage.getItem('token')
      ? '?token=' + localStorage.getItem('token') // return this query string if 'token' exists in local Storage
      : ''; // else set it to an empty string
    return this.http.post(this.host + token, body, {headers: headers}) // sets up an observable which holds the request
      .map((response: Response) => {
        const result = response.json();
        const message = new Message(
          result.obj.content,
          result.obj.user.firstName,
          result.obj._id,
          result.obj.user._id
        );
        this.messages.push(message);
        return message;
      }) // transforms data
      .catch((error: Response) => {
        this.errorService.handleError(error.json());
        return Observable.throw(error.json()); // here catch doesn't return an Observable as map so you need to define it
      });
  }

  getMessages() {
    return this.http.get(this.host)
      .map((response: Response) => {
        const messages = response.json().obj;
        let transformedMessages: Message[] = [];
        for (let message of messages) {
          transformedMessages.push(new Message(
            message.content,
            message.user.firstName,
            message._id,
            message.user._id
          ));
        }
        this.messages = transformedMessages;
        return transformedMessages;
      })
      .catch((error: Response) => {
        this.errorService.handleError(error.json());
        return Observable.throw(error.json()); // here catch doesn't return an Observable as map so you need to define it
      });
  }

  editMessage(message: Message) {
    this.messageIsEdit.emit(message);
  }

  updateMessage(message: Message) {
    const body = JSON.stringify(message);
    const headers = new Headers({'Content-Type': 'application/json'});
    const token = localStorage.getItem('token')
      ? '?token=' + localStorage.getItem('token') // return this query string if 'token' exists in local Storage
      : ''; // else set it to an empty string
    return this.http.patch(this.host + '/' + message.messageId + token, body, {headers: headers}) // sets up an observable which holds the request
      .map((response: Response) => response.json()) // transforms data
      .catch((error: Response) => {
        this.errorService.handleError(error.json());
        return Observable.throw(error.json()); // here catch doesn't return an Observable as map so you need to define it
      });
  }

  deleteMessage(message: Message) {
    this.messages.splice(this.messages.indexOf(message), 1);
    const token = localStorage.getItem('token')
      ? '?token=' + localStorage.getItem('token') // return this query string if 'token' exists in local Storage
      : ''; // else set it to an empty string
    return this.http.delete(this.host + '/' + message.messageId + token)
      .map((response: Response) => response.json()) // transforms data
      .catch((error: Response) => {
        this.errorService.handleError(error.json());
        return Observable.throw(error.json()); // here catch doesn't return an Observable as map so you need to define it
      });
  }
}
