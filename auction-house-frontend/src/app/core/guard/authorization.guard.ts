import {inject} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivateFn, Router} from '@angular/router';
import {UserInfoModel} from '../../shared/models/userInfo.model';


export const hasRole: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const router: Router = inject(Router);
  const requiredRoles: string[] = route.data['requiredRoles'];
  const loggedUser: UserInfoModel = getUser();

  return requiredRoles.includes(loggedUser.role)
    ? true
    : router.navigateByUrl('/dashboard/invalid-access');
};

const getUser = (): UserInfoModel => {
  return JSON.parse(localStorage.getItem('loggedUser') || '');
};

export const getLoggedEmail = (): string => {
  const loggedUser: UserInfoModel = getUser();
  return loggedUser.email;
};
