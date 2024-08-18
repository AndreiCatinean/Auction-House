import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserInfoModel} from "../../../shared/models/userInfo.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getInfo(): Observable<UserInfoModel> {
    return this.http.get<UserInfoModel>('userinfo/v1/info');
  }
}
