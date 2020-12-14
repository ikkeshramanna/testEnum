import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPersonDetails, PersonDetails } from 'app/shared/model/person-details.model';
import { PersonDetailsService } from './person-details.service';
import { PersonDetailsComponent } from './person-details.component';
import { PersonDetailsDetailComponent } from './person-details-detail.component';
import { PersonDetailsUpdateComponent } from './person-details-update.component';

@Injectable({ providedIn: 'root' })
export class PersonDetailsResolve implements Resolve<IPersonDetails> {
  constructor(private service: PersonDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((personDetails: HttpResponse<PersonDetails>) => {
          if (personDetails.body) {
            return of(personDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PersonDetails());
  }
}

export const personDetailsRoute: Routes = [
  {
    path: '',
    component: PersonDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonDetailsDetailComponent,
    resolve: {
      personDetails: PersonDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonDetailsUpdateComponent,
    resolve: {
      personDetails: PersonDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonDetailsUpdateComponent,
    resolve: {
      personDetails: PersonDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PersonDetails',
    },
    canActivate: [UserRouteAccessService],
  },
];
