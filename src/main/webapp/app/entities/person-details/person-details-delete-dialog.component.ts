import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonDetails } from 'app/shared/model/person-details.model';
import { PersonDetailsService } from './person-details.service';

@Component({
  templateUrl: './person-details-delete-dialog.component.html',
})
export class PersonDetailsDeleteDialogComponent {
  personDetails?: IPersonDetails;

  constructor(
    protected personDetailsService: PersonDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('personDetailsListModification');
      this.activeModal.close();
    });
  }
}
