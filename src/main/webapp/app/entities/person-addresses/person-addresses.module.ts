import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestEnumSharedModule } from 'app/shared/shared.module';
import { PersonAddressesComponent } from './person-addresses.component';
import { PersonAddressesDetailComponent } from './person-addresses-detail.component';
import { PersonAddressesUpdateComponent } from './person-addresses-update.component';
import { PersonAddressesDeleteDialogComponent } from './person-addresses-delete-dialog.component';
import { personAddressesRoute } from './person-addresses.route';

@NgModule({
  imports: [TestEnumSharedModule, RouterModule.forChild(personAddressesRoute)],
  declarations: [
    PersonAddressesComponent,
    PersonAddressesDetailComponent,
    PersonAddressesUpdateComponent,
    PersonAddressesDeleteDialogComponent,
  ],
  entryComponents: [PersonAddressesDeleteDialogComponent],
})
export class TestEnumPersonAddressesModule {}
