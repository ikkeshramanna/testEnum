import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPersonAddresses, PersonAddresses } from 'app/shared/model/person-addresses.model';
import { PersonAddressesService } from './person-addresses.service';
import { PersonAddressesComponent } from './person-addresses.component';
import { PersonAddressesDetailComponent } from './person-addresses-detail.component';
import { PersonAddressesUpdateComponent } from './person-addresses-update.component';

@Injectable({ providedIn: 'root' })
export class PersonAddressesResolve implements Resolve<IPersonAddresses> {
  constructor(private service: PersonAddressesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonAddresses> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((personAddresses: HttpResponse<PersonAddresses>) => {
          if (personAddresses.body) {
            return of(personAddresses.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PersonAddresses());
  }
}

export const personAddressesRoute: Routes = [
  {
    path: '',
    component: PersonAddressesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonAddresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonAddressesDetailComponent,
    resolve: {
      personAddresses: PersonAddressesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonAddresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonAddressesUpdateComponent,
    resolve: {
      personAddresses: PersonAddressesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonAddresses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonAddressesUpdateComponent,
    resolve: {
      personAddresses: PersonAddressesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonAddresses',
    },
    canActivate: [UserRouteAccessService],
  },
];
