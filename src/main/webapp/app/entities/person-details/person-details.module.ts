import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestEnumSharedModule } from 'app/shared/shared.module';
import { PersonDetailsComponent } from './person-details.component';
import { PersonDetailsDetailComponent } from './person-details-detail.component';
import { PersonDetailsUpdateComponent } from './person-details-update.component';
import { PersonDetailsDeleteDialogComponent } from './person-details-delete-dialog.component';
import { personDetailsRoute } from './person-details.route';

@NgModule({
  imports: [TestEnumSharedModule, RouterModule.forChild(personDetailsRoute)],
  declarations: [PersonDetailsComponent, PersonDetailsDetailComponent, PersonDetailsUpdateComponent, PersonDetailsDeleteDialogComponent],
  entryComponents: [PersonDetailsDeleteDialogComponent],
})
export class TestEnumPersonDetailsModule {}
