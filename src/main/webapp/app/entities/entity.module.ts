import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.TestEnumPersonModule),
      },
      {
        path: 'person-addresses',
        loadChildren: () => import('./person-addresses/person-addresses.module').then(m => m.TestEnumPersonAddressesModule),
      },
      {
        path: 'person-details',
        loadChildren: () => import('./person-details/person-details.module').then(m => m.TestEnumPersonDetailsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TestEnumEntityModule {}
