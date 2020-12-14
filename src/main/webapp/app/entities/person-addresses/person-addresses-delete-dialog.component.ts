import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonAddresses } from 'app/shared/model/person-addresses.model';
import { PersonAddressesService } from './person-addresses.service';

@Component({
  templateUrl: './person-addresses-delete-dialog.component.html',
})
export class PersonAddressesDeleteDialogComponent {
  personAddresses?: IPersonAddresses;

  constructor(
    protected personAddressesService: PersonAddressesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personAddressesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('personAddressesListModification');
      this.activeModal.close();
    });
  }
}
