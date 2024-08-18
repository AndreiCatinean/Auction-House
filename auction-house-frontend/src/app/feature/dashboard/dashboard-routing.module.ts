import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {hasRole} from '../../core/guard/authorization.guard';
import {InvalidAccessComponent} from '../../shared/components/invalid-access/invalid-access.component';
import {NotFoundComponent} from '../../shared/components/not-found/not-found.component';
import {ProductComponent} from './product/product.component';
import {ProductsComponent} from './products/products.component';
import {MyTransactionsComponent} from "./my-transactions/my-transactions.component";


export const routes: Routes = [
  {
    path: 'products',
    component: ProductsComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'USER']
    }
  },
  {
    path: 'product/:id',
    component: ProductComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN', 'USER']
    }
  },
  {
    path: 'listed-products',
    component: ProductsComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['USER']
    }
  },
  {
    path: 'fav-offers',
    component: ProductsComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['USER']
    }
  },
  {
    path: 'my-transactions',
    component: MyTransactionsComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['USER']
    }
  },

  {
    path: 'close-offers',
    component: ProductsComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },


  {
    path: 'invalid-access',
    component: InvalidAccessComponent
  },
  {
    path: 'not-found',
    component: NotFoundComponent
  },
  {
    path: '**',
    redirectTo: 'products'
  }
];

@NgModule({
  imports: [
    InvalidAccessComponent,
    NotFoundComponent,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class DashboardRoutingModule {
}
